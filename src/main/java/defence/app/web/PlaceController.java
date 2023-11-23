package defence.app.web;
import defence.app.model.bindingModel.CreatePlaceBindingModel;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.CategoryEnum;


import defence.app.model.viewModel.PlaceViewModel;
import defence.app.service.PictureService;
import defence.app.service.PlaceService;
import defence.app.service.UploadPictureService;
import defence.app.service.UserService;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;


@Controller
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;
    private final UploadPictureService uploadPictureService;
    private final PictureService pictureService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public PlaceController(PlaceService placeService, UploadPictureService uploadPictureService, PictureService pictureService, UserService userService, ModelMapper modelMapper) {
        this.placeService = placeService;
        this.uploadPictureService = uploadPictureService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("categories")
    public CategoryEnum[] categories() {
        return CategoryEnum.values();
    }

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("createPlaceBindingModel")) {
            model.addAttribute("createPlaceBindingModel", CreatePlaceBindingModel.empty());
        }
        model.addAttribute("categories", CategoryEnum.values());


        return "add";
    }


    @PostMapping("/add")
    public String addConfirm(
            @RequestParam("image") MultipartFile multipartFile,
            @Valid CreatePlaceBindingModel createPlaceBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createPlaceBindingModel", createPlaceBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createPlaceBindingModel",
                    bindingResult);

            return "redirect:/place/add";
        }

        String url = uploadPictureService.uploadPictureFile(multipartFile);

        String username = principal.getName();

        UserEntity author = userService.findFirstByUsername(username);

        Long placeId = placeService.addPlace(createPlaceBindingModel, username);

        pictureService.setPlace(placeId, url);

          return "redirect:/place/" + placeId;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") Long id, Model model) {

        PlaceViewModel placeViewModel = placeService
                .findViewModelById(id);



        model.addAttribute("place", placeViewModel);


        return "details"; // Името на шаблона, който ще бъде използван за показване на детайлите за мястото
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        placeService.deletePlace(id);
        return "redirect:/places/all";
    }

}





