package defence.app.init;

import defence.app.model.serviceModel.UserServiceModel;
import defence.app.service.CategoryService;
import defence.app.service.RoleService;
import defence.app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {

    private final UserService userService;

    private final CategoryService categoryService;

    private final RoleService roleService;

    public InitDB(UserService userService, CategoryService categoryService, RoleService roleService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {


        this.roleService.initRoles();

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
//Todo: Да добавя тук места с всички данни, за да са първоначално заредени в базата
//todo: Страница за търсене - като се открият конкретните места (напр. с fetch да се зареждат всички места БЕЗ снимките им, а само с една, която ми е основната с Ани)
//Todo: с fetch да се зареждат местата от категорията им в отделната им съответна страница
//todo: да използвам app.validation за Unique
//Todo: да оправя обясненията по проекта ми, за да е по - лесно за мен и за проверяващите
