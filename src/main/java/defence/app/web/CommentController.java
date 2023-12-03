package defence.app.web;

import defence.app.model.bindingModel.NewCommentBindingModel;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.viewModel.CommentViewModel;
import defence.app.service.CommentService;
import defence.app.service.PlaceService;
import defence.app.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


@Controller
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    private final PlaceService placeService;

    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, UserService userService, PlaceService placeService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.placeService = placeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/comments/place/{placeId}")
    public String getCommentsForPlace(@PathVariable Long placeId, Model model, RedirectAttributes redirectAttributes) {
        // Извличане на flash атрибутите и добавяне към модела
        if (redirectAttributes != null) {
            model.addAttribute("newCommentBindingModel", redirectAttributes.getFlashAttributes().get("newCommentBindingModel"));
            model.addAttribute("org.springframework.validation.BindingResult.newCommentBindingModel", redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.newCommentBindingModel"));
        }
        // Зареждане на информация за мястото
        PlaceEntity place = placeService.getPlaceById(placeId);
        model.addAttribute("place", place);

        List<CommentViewModel> comments = commentService.getAllCommentsForPlace(placeId);
        model.addAttribute("comments", comments);
        model.addAttribute("newCommentBindingModel", new NewCommentBindingModel());

        return "details";
    }

    @PostMapping("/comments/place/{placeId}")
    public String addComment(@PathVariable Long placeId,
                             @ModelAttribute("newCommentBindingModel")
                             @Valid NewCommentBindingModel newCommentBindingModel,
                             BindingResult bindingResult,
                             Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.newCommentBindingModel", bindingResult);
            model.addAttribute("place", placeService.getPlaceById(placeId));
            model.addAttribute("comments", commentService.getAllCommentsForPlace(placeId));
            return "details"; // Зареждаме отново същата страница с грешката
        }

        String username = principal.getName();

        // Валидацията е успешна, добавете коментара
        commentService.createComment(newCommentBindingModel, username, placeId);

//        return "redirect:/place/" + placeId;
        return "redirect:/comments/place/{placeId}";
    }
}