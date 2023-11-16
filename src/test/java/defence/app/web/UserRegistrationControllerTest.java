package defence.app.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRegistration() throws Exception {
        String uniqueUsername = "ani_" + System.currentTimeMillis(); // Генерирайте уникално потребителско име
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/register")
                                .param("firstName", "ani")
                                .param("lastName", "ani")
                                .param("email", "ani@ani.bg")
                                .param("username", uniqueUsername) // Използвайте уникалното потребителско име
                                .param("password", "1111")
                                .param("confirmPassword", "1111")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }
}