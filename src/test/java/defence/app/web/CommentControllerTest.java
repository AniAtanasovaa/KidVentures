package defence.app.web;

import defence.app.model.bindingModel.NewCommentBindingModel;
import defence.app.model.entity.CategoryEntity;
import defence.app.model.entity.PictureEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.viewModel.CommentViewModel;
import defence.app.service.CommentService;
import defence.app.service.PlaceService;
import defence.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private UserService userService;

    @Mock
    private PlaceService placeService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Principal principal;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCommentsForPlaceShouldReturnDetailsPage() {
        // Arrange
        Long placeId = 1L;
        PlaceEntity placeEntity = new PlaceEntity();
        // Добавям необходимите данни за теста
        placeEntity.setId(placeId);
        placeEntity.setAuthor(new UserEntity());
        placeEntity.setPicture(new PictureEntity());
        placeEntity.setCategory(new CategoryEntity());
        placeEntity.setCity("Sofia");
        placeEntity.setDescription("Description");
        placeEntity.setAddress("Mladost");
        placeEntity.setName("Kids place");
        List<CommentViewModel> comments = List.of(new CommentViewModel()); // Добавям необходимите данни за теста
        when(placeService.getPlaceById(placeId)).thenReturn(placeEntity);
        when(commentService.getAllCommentsForPlace(placeId)).thenReturn(comments);

        // Act
        String result = commentController.getCommentsForPlace(placeId, model, redirectAttributes);

        // Assert
        assertEquals("details", result);
        verify(model).addAttribute(eq("place"), eq(placeEntity));
        verify(model).addAttribute(eq("comments"), eq(comments));
        verify(model).addAttribute(eq("newCommentBindingModel"), any(NewCommentBindingModel.class));
    }

//    @Test
//    public void addCommentWithBindingErrorsShouldReturnDetailsPage() {
//        // Arrange
//        Long placeId = 1L;
//        NewCommentBindingModel newCommentBindingModel = new NewCommentBindingModel();
//        // Добавям необходимите данни за теста
//        BindingResult bindingResult = mock(BindingResult.class);
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//        // Act
//        String result = commentController.addComment(placeId, newCommentBindingModel, bindingResult, model, principal);
//
//        // Assert
//        assertEquals("details", result);
//        verify(model).addAttribute(eq("place"), any()); // Променено от isNull()
//        verify(model).addAttribute(eq("comments"), any(List.class));
//        verify(model).addAttribute(eq("newCommentBindingModel"), any(NewCommentBindingModel.class));
//        verify(model).addAttribute(eq(BindingResult.MODEL_KEY_PREFIX + "newCommentBindingModel"), eq(bindingResult));
//    }

    @Test
    public void addCommentWithSuccessfulValidationShouldRedirectToDetailsPage() {
        // Arrange
        Long placeId = 1L;
        NewCommentBindingModel newCommentBindingModel = new NewCommentBindingModel(); // Добавете необходимите данни за теста
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(principal.getName()).thenReturn("username");

        // Act
        String result = commentController.addComment(placeId, newCommentBindingModel, bindingResult, model, principal);

        // Assert
        assertEquals("redirect:/comments/place/{placeId}", result);
        verify(commentService).createComment(eq(newCommentBindingModel), eq("username"), eq(placeId));
    }
}