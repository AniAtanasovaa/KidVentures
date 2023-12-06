package defence.app.service.impl;
import defence.app.model.entity.RoleEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.RoleEnum;
import defence.app.model.serviceModel.UserServiceModel;
import defence.app.repository.RoleRepository;
import defence.app.repository.UserRepository;
import defence.app.service.UserService;
import defence.app.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; // Използвам Репото, защото ако инжектирам сървиса,
    // става кръгово извикване и се чупи
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper, ApplicationEventPublisher applicationEventPublisher) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void changeUserRole(String username, RoleEnum newRole) {

        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Премахвам текущата роля
            user.getRoles().clear();

            // Добавям новата роля
            Optional<RoleEntity> role = roleRepository.findByRole(newRole);

            RoleEntity newRoleEntity = role.orElseGet(() -> {

                RoleEntity roleEntity = new RoleEntity(newRole);

                return roleRepository.save(roleEntity);
            });

//Методът orElseGet, връща стойността от Optional, ако тя съществува,
// или генерира и връща стойност чрез подадена функция, ако няма такава стойност. Т.е. ако ролята
// съществува (е намерена), role.orElseGet(...) връща съществуващата роля. И ако ролята не съществува,
// тогава се изпълнява функцията, подадена като аргумент в orElseGet. Този блок код създава нова роля
// (RoleEntity roleEntity = new RoleEntity(newRole))
// с предоставеното име на ролята newRole и я записва в базата данни чрез roleRepository.save(roleEntity).

            user.getRoles().add(newRoleEntity);

            // Запазвам ъпдейта на потребителя в базата
            userRepository.save(user);

        } else {
            throw new ObjectNotFoundException("Не е открит потребител с това потребителско име: " + username);
        }

    }

    @Override
    public long countUserRegistrationsForDate(LocalDate date) {
        return userRepository.countByRegistrationDate(date);
    }

    @Override
    public Optional<UserEntity> getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //SecurityContextHolder.getContext(): Взема текущия контекст на сигурност. SecurityContextHolder е клас в
        // Spring Security, който предоставя механизъм за съхранение и достъп до информация за сигурността в
        // рамките на текущия поток на изпълнение.

        //getAuthentication(): Взема текущата аутентикация. Authentication представлява информацията за аутентикацията
        // на текущия потребител, като напри. дали е аутентикиран, ролите, които притежава и т.н.

        //getPrincipal(): Взема "основната" информация за аутентикацията, която обикновено представлява
        // потребителската информация. В повечето случаи, това е обект от тип UserDetails или неговите наследници.

        if (principal instanceof UserDetails) { //Проверявам дали стойността на principal е инстанция на UserDetails

            UserDetails userDetails = (UserDetails) principal;

//     ///Ако principal е инстанция на UserDetails, означава, че текущият потребител е успешно аутентикиран
//     в системата и можем да извлечем допълнителна информация от UserDetails (напр. потребителското име)
//      и да я използваме за взимане на информация за потребителя от базата дани - т.е трябва да имам поле username в UserDetails, което съдържа потребителското име

            String username = userDetails.getUsername();

            // Връщам информацията за текущия потребител от базата данни
            return userRepository.findByUsername(username);
        }

        // Ако не успеем да вземем потребител, връщаме null
        return null;
    }


    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        // Използвам UserServiceModel, като преходен обект, който събира и валидира входните данни,
        //преди да се извърши операцията с базата данни, защото правя валидация на данните, преди те да бъдат
        // изпратени към базата данни (чрез анотациите @NotBlank, @Size и пр.)

        userRepository.save(map(userServiceModel));

    }

    private UserEntity map(UserServiceModel userServiceModel) {

        UserEntity userEntity = new UserEntity();

        Optional<RoleEntity> role = roleRepository.findByRole(RoleEnum.USER);

        RoleEntity userRole = role.orElseGet(() -> {
            RoleEntity roleEntity = new RoleEntity(RoleEnum.USER);
            return roleRepository.save(roleEntity);
        });

        userEntity.setFirstName(userServiceModel.getFirstName())
                .setLastName(userServiceModel.getLastName())
                .setEmail(userServiceModel.getEmail())
                .setUsername(userServiceModel.getUsername()).setRegistrationDate(LocalDate.now())
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .getRoles().add(userRole);

        return userEntity;
    }


    @Override
    public void registerAdminUser(UserServiceModel adminUser) {
        userRepository.save(mapAdmin(adminUser));
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findFirstByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @Override
    public Optional<UserEntity> findById(Long authorId) {
        return userRepository.findById(authorId);
    }

    @Override
    public boolean isUserTableNull() {
        return userRepository.count() == 0;
    }


    private UserEntity mapAdmin(UserServiceModel adminUser) {

        UserEntity adminEntity = new UserEntity();

        Optional<RoleEntity> roleUser = roleRepository.findByRole(RoleEnum.USER);

        RoleEntity userRole = roleUser.orElseGet(() -> {
            RoleEntity roleEntity = new RoleEntity(RoleEnum.USER);
            return roleRepository.save(roleEntity);
        });


        Optional<RoleEntity> role = roleRepository.findByRole(RoleEnum.ADMIN);

        RoleEntity adminRole = role.orElseGet(() -> {
            RoleEntity roleEntity = new RoleEntity(RoleEnum.ADMIN);
            return roleRepository.save(roleEntity);
        });

        adminEntity.setFirstName(adminUser.getFirstName())
                .setLastName(adminUser.getLastName())
                .setEmail(adminUser.getEmail())
                .setUsername(adminUser.getUsername())
                .setPassword(passwordEncoder.encode(adminUser.getPassword()))
                .getRoles().add(adminRole);

        adminEntity.getRoles().add(userRole);


        return adminEntity;
    }

    @Override
    public UserEntity findByUsernameAndPassword(String username, String password) {


        return userRepository.findByUsernameAndPassword(username, password)

                .orElse(null);
    }


    @Override
    public boolean isAdminUserExists() {
        return userRepository.existsByRoles_Role(RoleEnum.ADMIN);
    }

}
