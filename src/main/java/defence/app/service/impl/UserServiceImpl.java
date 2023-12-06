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
//Todo при регистрация на потребител с данни, които вече са на друг потребител, не изкарва грешка, но полсе същият потребител, който лпреди е можел да се регистрира, вече не може дасе логне

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
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

            user.getRoles().add(newRoleEntity);

            // Запазваме ъпдейта на потребителя
            userRepository.save(user);
        } else {
            throw new ObjectNotFoundException("User not found with username: " + username);
        }

    }

    @Override
    public long countUserRegistrationsForDate(LocalDate date) {
        return  userRepository.countByRegistrationDate(date);
    }

    @Override
    public Optional<UserEntity> getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;
            // Предполагаме, че има поле username в UserDetails, което съдържа потребителското име
            String username = userDetails.getUsername();

            // Връщаме информацията за текущия потребител от базата данни или друго хранилище
            return userRepository.findByUsername(username);
        }

        // Ако не успеем да вземем потребител, връщаме null или хвърляме изключение
        return null;
    }



    @Override
    public void registerUser(UserServiceModel userServiceModel) {

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
        return userRepository.count()==0;
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
    public UserEntity findByUsernameAndPassword(String username, String password) { //Todo да оправя да не връща ентити , а сървис мидел


        return userRepository. findByUsernameAndPassword(username, password)

                .orElse(null);
    }


    @Override
    public boolean isAdminUserExists() {
        return userRepository.existsByRoles_Role(RoleEnum.ADMIN);
    }

}
