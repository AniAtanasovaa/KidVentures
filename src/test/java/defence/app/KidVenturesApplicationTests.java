package defence.app;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KidVenturesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		// Проверка дали контекстът на приложението се зарежда успешно
	}

	@Test
	void testHomePage() throws Exception {
		mockMvc.perform(get("/"))  // Можете да замените "/" с пътя на вашата начална страница
				.andExpect(status().isOk());  // Проверка за успешен HTTP отговор (200 OK)
	}

}

	//Интеграционните тестове са вид тестване, които се фокусират върху проверката на взаимодействието между
	// различни компоненти или системи в приложението.
	// Тези тестове се използват за уверяване, че различните части на софтуерното приложение работят заедно
	// коректно.
//покриват функционалности, които изискват взаимодействие между различни компоненти или системи, което обикновено не
// може да бъде напълно изпробвано с юнит тестове.
//
//Поведение на системата като цяло: Интеграционните тестове имат за цел да проверят дали системата като
// цяло работи правилно, след като компонентите са интегрирани.


