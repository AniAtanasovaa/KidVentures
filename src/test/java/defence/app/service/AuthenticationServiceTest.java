package defence.app.service;
import defence.app.model.entity.UserEntity;
import defence.app.service.impl.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthenticationServiceTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getLoggedInUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        // Мокиране на SecurityContextHolder и Authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEntity);

        // Извикване на метода, който тестваме
        UserEntity loggedInUser = authenticationService.getLoggedInUser();

        // Проверка дали резултата отговаря на очаквания резултат
        assertEquals(userEntity, loggedInUser);
    }

    @Test
    void getLoggedInUserWhenNotAuthenticated() {
        // Мокиране на SecurityContextHolder и Authentication
        when(securityContext.getAuthentication()).thenReturn(null);

        // Извикване на метода, който тестваме
        UserEntity loggedInUser = authenticationService.getLoggedInUser();

        // Проверка дали връща null, когато потребителят не е аутентикиран
        assertEquals(null, loggedInUser);
    }

    @Test
    void getLoggedInUserWhenPrincipalNotUserEntity() {
        // Мокиране на SecurityContextHolder и Authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("notUserEntity");

        // Извикване на метода, който тестваме
        UserEntity loggedInUser = authenticationService.getLoggedInUser();

        // Проверка дали връща null, когато principal не е от тип UserEntity
        assertEquals(null, loggedInUser);
    }
}