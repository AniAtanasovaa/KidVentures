package defence.app.web;

import defence.app.repository.CommentRepository;
import defence.app.repository.UserRepository;
import defence.app.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;
//    @BeforeEach
//    void setUp() {
//        // Изтриване на коментарите преди всяко тестване
//        commentRepository.deleteAll();
//        userRepository.deleteAll();
//    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSuccessfulRegistration() throws Exception {
        String uniqueEmail = "ani_" + System.currentTimeMillis() + "@ani.bg";
        String uniqueUsername = "ani_" + System.currentTimeMillis() + "@ani";

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/register")
                                .param("firstName", "ani")
                                .param("lastName", "ani")
                                .param("email", uniqueEmail)
                                .param("username", uniqueUsername)
                                .param("password", "1111")
                                .param("confirmPassword", "1111")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }

    @Test
    void testRegistrationWithNonMatchingPasswords() throws Exception {
        String uniqueEmail = "ani_" + System.currentTimeMillis() + "@ani.bg";
        String uniqueUsername = "ani_" + System.currentTimeMillis() + "@ani";

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/register")
                                .param("firstName", "ani")
                                .param("lastName", "ani")
                                .param("email", uniqueEmail)
                                .param("username", uniqueUsername)
                                .param("password", "1111")
                                .param("confirmPassword", "1234")  // Несъвпадащи пароли
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));  // Проверка за пренасочване обратно към регистрацията
    }


}
