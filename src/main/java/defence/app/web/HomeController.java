package defence.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

   @GetMapping("/home")
   public String index() {
       return "index";
   }


    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
