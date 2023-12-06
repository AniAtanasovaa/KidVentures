package defence.app.service.impl;

import defence.app.model.entity.RoleEntity;
import defence.app.model.enums.RoleEnum;
import defence.app.repository.RoleRepository;
import defence.app.service.RoleService;
import defence.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    private final UserService userService;

    public RoleServiceImpl(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }


    @Override
    public void initRoles() {

        if (userService.isUserTableNull()==false) {
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
}
