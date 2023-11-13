package defence.app.web;
import defence.app.model.entity.UserEntity;
import defence.app.model.bindingModel.UserLoginBindingModel;
import defence.app.repositories.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
public class UserLoginController {

    private final UserRepository userService;

    private final ModelMapper modelMapper;

    public UserLoginController(UserRepository userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel () {
        return new UserLoginBindingModel();
    }

    @GetMapping("/login")
    public String login(Model model) {

        if( ! model.containsAttribute("isFound"))  {
            model.addAttribute("isFound", true); }
        return "login";
    }


    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Optional<UserEntity> userEntity = userService.findByUsernameAndPassword(userLoginBindingModel.getUsername(),
                userLoginBindingModel.getPassword());


        if (!userEntity.isPresent()||bindingResult.hasErrors()) {


            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                    bindingResult);

            return "redirect:/login";

        }

        return "redirect:/";

    }


    @PostMapping("/login-error")
    public String onFailure(
            @ModelAttribute("username") String username,
            Model model) {

        model.addAttribute("username", username);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }
}