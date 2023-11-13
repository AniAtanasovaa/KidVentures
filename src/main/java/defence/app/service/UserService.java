package defence.app.service;

import defence.app.model.entity.UserEntity;
import defence.app.model.serviceModel.UserServiceModel;

import java.util.Optional;

public interface UserService {

    void initRoles();



    void registerUser(UserServiceModel userServiceModel);

   UserEntity findByUsernameAndPassword(String username, String password);

    boolean isAdminUserExists();

    void registerAdminUser(UserServiceModel adminUser);

    Optional<UserEntity> findByUsername(String username);

    UserEntity findFirstByUsername(String username);

    Optional<UserEntity> findById(Long authorId);


    //ToDo да оправя да няма Никъде Mobilele - в head
}
