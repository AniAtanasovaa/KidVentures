package defence.app.web;
import defence.app.model.bindingModel.CreatePlaceBindingModel;
import defence.app.model.enums.CategoryEnum;
import defence.app.service.UploadPictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class UploadPictureController {

    private final UploadPictureService uploadPictureService;

    public UploadPictureController(UploadPictureService uploadPictureService) {
        this.uploadPictureService = uploadPictureService;
    }



//    @GetMapping("/add")//this method is the default access point to the application and it returns a web page named add.html that contains the form.
//    public String add(Model model) {
//
//        if (!model.containsAttribute("createPlaceBindingModel")) {
//            model.addAttribute("createPlaceBindingModel", CreatePlaceBindingModel.empty());
//        }
//        model.addAttribute("categories", CategoryEnum.values());
//
//
//        return "add";
//    }
    @PostMapping("/upload") //this method creates the file on the cloud and returns a web page named details.html.
    public String upload(@RequestParam("image") MultipartFile multipartFile,
                         RedirectAttributes redirectAttributes) throws IOException {

        String imageURL = uploadPictureService.uploadPictureFile(multipartFile);

        redirectAttributes.addFlashAttribute("imageURL", imageURL);

        return "redirect:/place/add";
    }
}
