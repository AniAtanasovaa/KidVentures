package defence.app.web;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GlobalErrorHandlingControllerTest {

    @Mock
    private ObjectNotFoundException objectNotFoundException;

    @Mock
    private ServerErrorException serverErrorException;

    @Mock
    private AccessDeniedException accessDeniedException;

    @InjectMocks
    private GlobalErrorHandlingController errorHandlingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleNotFound() {
        // Arrange
        when(objectNotFoundException.getIdentifier()).thenReturn("testIdentifier");

        // Act
        ModelAndView modelAndView = errorHandlingController.handleNotFound(objectNotFoundException);

        // Assert
        assertEquals("error/404", modelAndView.getViewName());
        // Можете да добавите други проверки, ако се налага
    }

    @Test
    void testHandleServerError() {
        // Act
        ModelAndView modelAndView = errorHandlingController.handleServerError(serverErrorException);

        // Assert
        assertEquals("error/500", modelAndView.getViewName());
        // Можете да добавите други проверки, ако се налага
    }

    @Test
    void testHandleAccessDeniedError() {
        // Act
        ModelAndView modelAndView = errorHandlingController.handleAccessDeniedError(accessDeniedException);

        // Assert
        assertEquals("error/access-denied", modelAndView.getViewName());
        // Можете да добавите други проверки, ако се налага
    }
}