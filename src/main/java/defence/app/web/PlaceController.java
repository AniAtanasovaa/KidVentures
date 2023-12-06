package defence.app.web;
import defence.app.model.bindingModel.CreatePlaceBindingModel;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.CategoryEnum;


import defence.app.model.viewModel.PlaceViewModel;
import defence.app.service.*;

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

    private final CommentService commentService;

    public PlaceController(PlaceService placeService, UploadPictureService uploadPictureService, PictureService pictureService, UserService userService, ModelMapper modelMapper, CommentService commentService) {
        this.placeService = placeService;
        this.uploadPictureService = uploadPictureService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.commentService = commentService;
    }

    @ModelAttribute("categories")
    public CategoryEnum[] categories() {
        return CategoryEnum.values();
    }

    @GetMapping("/add")
    public String add(Model model,
                      @ModelAttribute("imageURL") String imageURL) {

        if (!model.containsAttribute("createPlaceBindingModel")) {
            model.addAttribute("createPlaceBindingModel", CreatePlaceBindingModel.empty());
        }
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("imageURL", imageURL);

        return "add";
    }


    @PostMapping("/add")
    public String addConfirm(
            @Valid CreatePlaceBindingModel createPlaceBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Principal principal,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createPlaceBindingModel", createPlaceBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createPlaceBindingModel",
                    bindingResult);

            return "redirect:/place/add";
        }



        String username = principal.getName();

        UserEntity author = userService.findFirstByUsername(username);

        Long placeId = placeService.addPlace(createPlaceBindingModel, username);

        String imageURL = uploadPictureService.uploadPictureFile(multipartFile);

        pictureService.setPlace(placeId, imageURL);

        // Добавям коментара по подразбиране към новосъздаденото място
        commentService.createDefaultComment(placeId);

        return "redirect:/place/" + placeId;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        PlaceViewModel placeViewModel = placeService.findViewModelById(id);
        model.addAttribute("place", placeViewModel);
        model.addAttribute("imageURL", placeViewModel.getPicture().getUrl());
        return "details";
    }



    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        placeService.deletePlace(id);
        return "redirect:/places/all";
    }

}