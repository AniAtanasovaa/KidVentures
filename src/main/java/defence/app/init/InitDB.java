package defence.app.init;

import defence.app.model.serviceModel.UserServiceModel;
import defence.app.service.CategoryService;
import defence.app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {

    private final UserService userService;

    private final CategoryService categoryService;

    public InitDB(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {


        this.userService.initRoles();

        if (!userService.isAdminUserExists()) {

            UserServiceModel adminUser = new UserServiceModel();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin");
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Admin");
            adminUser.setEmail("admin@example.com");

            userService.registerAdminUser(adminUser);
        }

        this.categoryService.initCategories();

    }
}
