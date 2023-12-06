package defence.app.web;

import defence.app.model.bindingModel.UserLoginBindingModel;
import defence.app.model.entity.UserEntity;
import defence.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserLoginControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserLoginController userLoginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testLoginSuccess() {
//        // Arrange
//        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
//        BindingResult bindingResult = mock(BindingResult.class);
//        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//
//        // Mock the UserRepository response
//        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(new UserEntity()));
//
//        // Act
//        String result = userLoginController.loginConfirm(userLoginBindingModel, bindingResult, redirectAttributes);
//
//        // Assert
//        assertEquals("redirect:/", result);
//        verify(redirectAttributes, times(0)).addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
//        verify(redirectAttributes, times(0)).addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
//    }
    @Test
    public void testLoginFailure() {
        // Arrange
        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Model model = new ExtendedModelMap();

        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.empty());
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = userLoginController.loginConfirm(userLoginBindingModel, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/login", result);
        verify(redirectAttributes).addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
    }

    @Test
    public void testOnFailure() {
        // Arrange
        Model model = new ExtendedModelMap();

        // Act
        String result = userLoginController.onFailure("username", model);

        // Assert
        assertEquals("login", result);
        assertEquals("username", model.getAttribute("username"));
        assertEquals("true", model.getAttribute("bad_credentials"));
    }
}