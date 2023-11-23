package defence.app.web;

import defence.app.service.UploadPictureService;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile multipartFile
            , RedirectAttributes redirectAttributes) throws IOException {

        String url = uploadPictureService.uploadPictureFile(multipartFile);

        redirectAttributes.addFlashAttribute("url", url);

        return "redirect:/place/add";
    }
}
