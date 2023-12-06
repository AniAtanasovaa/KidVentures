package defence.app.web;
import defence.app.model.bindingModel.RoleChangeForm;
import defence.app.model.entity.RoleEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.RoleEnum;
import defence.app.repository.RoleRepository;
import defence.app.repository.UserRepository;
import defence.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void changeRoleGetMapping() {
        // Извикване на GET заявка
        String viewName = adminController.changeRole(model);

        // Проверка дали моделът е бил подаден на изгледа
        verify(model, times(1)).addAttribute(eq("roleChangeForm"), any(RoleChangeForm.class));

        // Проверка дали е върнатият изглед е правилният
        assertEquals("change", viewName);
    }

    @Test
    void changeUserRoleSuccess() {
        // Подготовка на мокове
        RoleChangeForm roleChangeForm = new RoleChangeForm();
        roleChangeForm.setUsername("testUser");
        roleChangeForm.setNewRole(RoleEnum.ADMIN);

        // Подготовка на мокове
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
        when(roleRepository.findByRole(RoleEnum.ADMIN)).thenReturn(Optional.of(new RoleEntity(RoleEnum.ADMIN)));

        // Извикване на POST заявка
        String viewName = adminController.changeUserRole(roleChangeForm, model);

        // Проверка дали моделът не съдържа грешка
        verify(model, never()).addAttribute(eq("error"), anyString());

        // Проверка дали е върнатият изглед е правилният
        assertEquals("changed", viewName);
    }

//    @Test
//    void changeUserRoleUserNotFound() {
//        // Подготовка на мокове
//        RoleChangeForm roleChangeForm = new RoleChangeForm();
//        roleChangeForm.setUsername("nonexistentUser");
//        roleChangeForm.setNewRole(RoleEnum.ADMIN);
//
//        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());
//
//        // Извикване на POST заявка
//        String viewName = adminController.changeUserRole(roleChangeForm, model);
//
//        // Проверка дали моделът съдържа грешка
//        verify(model, times(1)).addAttribute(eq("error"), anyString());
//
//        // Проверка дали е върнатият изглед е правилният
//        assertEquals("change", viewName);
//    }
}