package defence.app.service.impl;
import defence.app.model.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceIntegrationTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testGetLoggedInUser() {
        // Arrange
        UserEntity expectedUser = new UserEntity(); //Създава се обект от тип UserEntity, представляващ очаквания резултат от метода getLoggedInUser
        expectedUser.setId(1L);
        Authentication authentication = mock(Authentication.class); //Създава се мок обект Authentication, който ще бъде върнат от SecurityContextHolder.
    when(authentication.getPrincipal()).thenReturn(expectedUser); //Указва се, че при извикване на authentication.getPrincipal(), ще се върне създадения UserEntity.

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext); //Създава се мок на SecurityContext, който при извикване на securityContext.getAuthentication() връща мока Authentication.
       // SecurityContextHolder се конфигурира да връща създадения SecurityContext

        // Act
        UserEntity actualUser = authenticationService.getLoggedInUser();

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testGetLoggedInUserWhenNotAuthenticated() {
        // Arrange
        SecurityContextHolder.clearContext(); //Изчиства се SecurityContextHolder, симулирайки липса на аутентикация.

        // Act
        UserEntity actualUser = authenticationService.getLoggedInUser();

        // Assert
        assertEquals(null, actualUser);
    }
}