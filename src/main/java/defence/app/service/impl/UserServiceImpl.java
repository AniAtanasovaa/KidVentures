package defence.app.service.impl;

import defence.app.model.entity.RoleEntity;
import defence.app.model.entity.UserEntity;

import defence.app.model.enums.RoleEnum;
import defence.app.model.serviceModel.UserServiceModel;

import defence.app.repositories.RoleRepository;
import defence.app.repositories.UserRepository;
import defence.app.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import java.util.Optional;
//Todo при регистрация на потребител с данни, които вече са на друг потребител, не изкарва грешка, но полсе същият потребител, който лпреди е можел да се регистрира, вече не може дасе логне

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initRoles() {

        if (userRepository.count() != 0) {
            return;
        }

        Arrays.stream(RoleEnum.values()).forEach(roleEnum -> {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRole(roleEnum);

            switch (roleEnum) {

                case ADMIN -> roleEntity.setDescription("This is the administrator");
                case USER -> roleEntity.setDescription("This is the default role for every newly registered user");
            }

            roleRepository.save(roleEntity);

        });
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
                .setUsername(userServiceModel.getUsername())
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


//ToDo да сложа картинка отляво на пътя
    //Todo ErrorHandling от остава 1.32 мин


    @Override
    public boolean isAdminUserExists() {
        return userRepository.existsByRoles_Role(RoleEnum.ADMIN);
    }

}
