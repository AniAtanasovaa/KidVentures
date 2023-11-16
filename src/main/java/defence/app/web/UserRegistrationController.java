package defence.app.web;
import defence.app.model.bindingModel.UserRegistrationBindingModel;
import defence.app.model.serviceModel.UserServiceModel;
import defence.app.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserRegistrationController {

  private  final  UserService userService;
    private final ModelMapper modelMapper;

    public UserRegistrationController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;

        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public UserRegistrationBindingModel userRegistrationBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


   @PostMapping("/register")
    public String registerConfirm(@Valid UserRegistrationBindingModel userRegistrationBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {


       if(bindingResult.hasErrors() || !userRegistrationBindingModel.getPassword().equals(userRegistrationBindingModel
               .getConfirmPassword())) {

           redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
           redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel",
                   bindingResult);

           return "redirect:register";
       }



       userService.registerUser(modelMapper.map(userRegistrationBindingModel, UserServiceModel.class));

        return "redirect:/login";
   }

}
