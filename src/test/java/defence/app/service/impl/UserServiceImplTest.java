package defence.app.service.impl;
import defence.app.model.entity.RoleEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.RoleEnum;
import defence.app.model.serviceModel.UserServiceModel;
import defence.app.repository.RoleRepository;
import defence.app.repository.UserRepository;
import defence.app.service.RoleService;
import defence.app.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void changeUserRoleUserExists() {
        // Подготовка на мокове
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
        when(roleRepository.findByRole(RoleEnum.ADMIN)).thenReturn(Optional.of(new RoleEntity(RoleEnum.ADMIN)));

        // Извикване на тествания метод
        userService.changeUserRole("testUser", RoleEnum.ADMIN);

        // Проверка дали ролята на потребителя е променена
        assertEquals(1, userEntity.getRoles().size());
        assertTrue(userEntity.getRoles().stream().anyMatch(role -> role.getRole() == RoleEnum.ADMIN));
    }

    @Test
    void changeUserRoleUserNotFound() {
        // Сценарий, когато потребителят не съществува
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        // Извиквам тествания метод и проверка за хвърлено изключение
        assertThrows(ObjectNotFoundException.class, () -> userService.changeUserRole("nonexistentUser", RoleEnum.ADMIN));

        // Уверявам се, че потребителят не е бил запазен
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void countUserRegistrationsForDate() {
        // Сценарий с потребители, регистрирани на дадената дата
        when(userRepository.countByRegistrationDate(LocalDate.now())).thenReturn(5L);

        // Извикване на тествания метод
        long count = userService.countUserRegistrationsForDate(LocalDate.now());

        // Проверка дали резултатът е правилен
        assertEquals(5, count);
    }

    @Test
    void registerUser() {
        // Подготовка на мокове
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("testUser");

        when(roleRepository.findByRole(RoleEnum.USER)).thenReturn(Optional.of(new RoleEntity(RoleEnum.USER)));

        // Извикване на тествания метод
        userService.registerUser(userServiceModel);

        // Проверка дали потребителят е бил запазен
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}