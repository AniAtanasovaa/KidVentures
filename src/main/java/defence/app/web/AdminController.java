package defence.app.web;
import defence.app.model.bindingModel.RoleChangeForm;
import defence.app.service.UserService;
import defence.app.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change")
    public String changeRole(Model model) {

        model.addAttribute("roleChangeForm", new RoleChangeForm()); // Обект за формата
        return "change";
    }


    @PostMapping("/change")
    public String changeUserRole(@ModelAttribute RoleChangeForm roleChangeForm, Model model) {

        try {
            userService.changeUserRole(roleChangeForm.getUsername(), roleChangeForm.getNewRole());
            return "changed";
        } catch (ObjectNotFoundException e) {
            model.addAttribute("error", e.getMessage());

        } catch (Exception e) {
            model.addAttribute("error", "Възникна грешка при смяна на ролята");
        }
        return "change";
    }

//    @PostMapping("/change") // За REST CONTOLLER
//    public ResponseEntity<String> changeUserRole(@ModelAttribute RoleChangeForm roleChangeForm) {
//        try {
//            userService.changeUserRole(roleChangeForm.getUsername(), roleChangeForm.getNewRole());
//            return ResponseEntity.ok("Ролята е променена успешно");
//        } catch (ObjectNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Възникна грешка при смяна на ролята");
//        }
//    }
}
