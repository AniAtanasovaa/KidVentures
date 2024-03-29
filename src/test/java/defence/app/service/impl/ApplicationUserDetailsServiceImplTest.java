package defence.app.service.impl;

import defence.app.model.entity.RoleEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.RoleEnum;
import defence.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //Аннотацията @ExtendWith(MockitoExtension.class) се използва за интегриране на Mockito с JUnit 5 тестовете.
public class ApplicationUserDetailsServiceImplTest {


    private ApplicationUserDetailsService testApplicationUserDetailsService;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        testApplicationUserDetailsService = new ApplicationUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserNotFound() { //Този тест проверява дали се хвърля изключение от тип UsernameNotFoundException,
        // когато потребителят не е намерен в репозиторито.

        Assertions.assertThrows(UsernameNotFoundException.class, ()->
                testApplicationUserDetailsService.loadUserByUsername("albena"));
    }

    @Test
    void testUserFound() {


        // Arrange - подговотка на тестовите данни т.е. в случая са 1 ново UserEntity
        UserEntity testUserEntity = createTestUser();

//   Създава се тестов UserEntity и се подготвя резултата от репозиторито чрез Mockito.
//   Когато се извика findByUsername със съответното потребителско име, той ще върне Optional.of(testUserEntity).

        when(mockUserRepository.findByUsername(testUserEntity.getUsername()))
                .thenReturn(Optional.of(testUserEntity));

        // Act - Взимаме това, което тестваме
        UserDetails userDetails =
               testApplicationUserDetailsService.loadUserByUsername(testUserEntity.getUsername());
        //Методът loadUserByUsername на ApplicationUserDetailsServiceImpl се извиква със съответното
        // потребителско име, и връща обект от тип UserDetails


        // Assert
        //AssertNotNull проверява дали върнатият UserDetails обект не е null
       Assertions.assertNotNull(userDetails);

        assertEquals( // Първи аргумент - това, което очакваме,
                // 2ри  аргумент - това, което реално е дошло, 3ти  аргумент (опционален) - съобщение за грешка
                testUserEntity.getUsername(),
                userDetails.getUsername(),
                "Username is not populated properly.");


        assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());

        assertTrue(
                containsAuthority(userDetails, "ROLE_" + RoleEnum.ADMIN),
                "The user is not admin");
        assertTrue(
                containsAuthority(userDetails, "ROLE_" + RoleEnum.USER),
                "The user is not user");
}


    private UserEntity createTestUser() {

    return new UserEntity().setUsername("аni").setEmail("ani@abv.bg").setFirstName("аni").setLastName("аni")
            .setPassword("1234").setRoles
                    (
                            List.of(new RoleEntity().setRole(RoleEnum.USER),
                                    new RoleEntity().setRole(RoleEnum.ADMIN)));
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(authority -> expectedAuthority.equals(authority.getAuthority()));
    }


}
