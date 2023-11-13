package defence.app.web;
import defence.app.model.bindingModel.CreatePlaceBindingModel;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.CategoryEnum;
import defence.app.model.serviceModel.PlaceServiceModel;

import defence.app.model.viewModel.PlaceViewModel;
import defence.app.service.PlaceService;
import defence.app.service.UserService;
import defence.app.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    private final UserService userService;
    private final ModelMapper modelMapper;

    public PlaceController(PlaceService placeService, UserService userService, ModelMapper modelMapper) {
        this.placeService = placeService;
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

            @Valid CreatePlaceBindingModel createPlaceBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createPlaceBindingModel", createPlaceBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createPlaceBindingModel",
                    bindingResult);
            return "redirect:/place/add";
        }

        String username = principal.getName();

        UserEntity author = userService.findFirstByUsername(username);

     String imageUrl = createPlaceBindingModel.getPictureUrl();

        PlaceServiceModel placeServiceModel = modelMapper
                .map(createPlaceBindingModel, PlaceServiceModel.class);

        placeServiceModel.setAuthor(author);
        placeServiceModel.setImageUrl(imageUrl);

        Long placeId = placeService.addPlace(placeServiceModel, username);

          return "redirect:/place/" + placeId;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") Long id, Model model) {

        PlaceViewModel placeViewModel = placeService
                .findViewModelById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Не е намерено място с това ид" + id + "!"));



        model.addAttribute("place", placeViewModel);


        return "details"; // Името на шаблона, който ще бъде използван за показване на детайлите за мястото
    }

//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        placeService.deletePlace(id);
//        return "redirect:/places/all";
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        placeService.deletePlace(id);
        return "redirect:/places/all";
    }

}





