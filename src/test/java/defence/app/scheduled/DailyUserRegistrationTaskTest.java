package defence.app.scheduled;
import defence.app.model.entity.DailyStatisticsEntity;
import defence.app.repository.DailyStatisticsRepository;
import defence.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DailyUserRegistrationTaskTest {

    @Mock //Използва се за създаване на мок обекти, които ще заменят реалните обекти в тестовете.
    private UserService userService;

    @Mock
    private DailyStatisticsRepository dailyStatisticsRepository;

    @InjectMocks //Използва се за инжектиране на мок обекти в обекта, който тестваме.
    private DailyUserRegistrationTask dailyUserRegistrationTask;

    @BeforeEach //Анотация, която обозначава метод, който се изпълнява преди всеки тест. В този случай, се инициализират мок обектите.
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void recordDailyUserRegistrations() {
        LocalDate today = LocalDate.now();
        long dailyUserRegistrations = 10L;

        // Подготвяме мокове и заявки
        when(userService.countUserRegistrationsForDate(today)).thenReturn(dailyUserRegistrations);
        //Конфигурира мок обекта userService да върне стойността на dailyUserRegistrations,
        // когато се извика метода

        dailyUserRegistrationTask.recordDailyUserRegistrations(); //Извиква метода,
        // който води статистика за дневните регистрации.

        // Хващаме аргумента, който се подава на save метода
        ArgumentCaptor<DailyStatisticsEntity> captor = ArgumentCaptor.forClass(DailyStatisticsEntity.class);
        verify(dailyStatisticsRepository, times(1)).save(captor.capture());
//Проверява дали методът save на мок обекта dailyStatisticsRepository е бил извикан точно веднъж
// и хваща аргумента, който се е подавал при извикването му.


        // Проверяваме дали аргументът има правилните стойности
        DailyStatisticsEntity capturedEntity = captor.getValue();
        assertEquals(today, capturedEntity.getDate()); //Проверява дали датата на записаната събитие в статистиката съвпада с текущата дата.
        assertEquals(dailyUserRegistrations, capturedEntity.getDailyRegistrations()); //Проверява дали броят регистрации в статистиката е съвпада с очаквания.
    }
    @Test
    void saveDailyStatistics() {
        LocalDate date = LocalDate.now();
        long dailyUserRegistrations = 5L;

        // Подготвяме мокове и заявки
        DailyStatisticsEntity existingDailyStatistics = new DailyStatisticsEntity();
        existingDailyStatistics.setDate(date);

        when(dailyStatisticsRepository.findByDate(date)).thenReturn(existingDailyStatistics);
        //Конфигурация на мок обекта dailyStatisticsRepository да върне съществуваща статистика,
        // когато се извика метода findByDate с аргумент date.

        // Извикваме метода, който тестваме
        dailyUserRegistrationTask.saveDailyStatistics(date, dailyUserRegistrations);

        // Хващаме аргумента, който се подава на save метода
        ArgumentCaptor<DailyStatisticsEntity> captor = ArgumentCaptor.forClass(DailyStatisticsEntity.class);
        verify(dailyStatisticsRepository, times(1)).save(captor.capture());

        // Проверяваме дали аргументът има правилните стойности
        DailyStatisticsEntity capturedEntity = captor.getValue();
        assertEquals(date, capturedEntity.getDate());
        assertEquals(dailyUserRegistrations, capturedEntity.getDailyRegistrations());
    }
}

//ArgumentCaptor е инструмент в библиотеката Mockito, който се използва в тестовете, за да хване (capture) стойностите, подавани към методите по време на тест.
//Когато искате да проверите какви аргументи са били подадени при извикването на определен метод, можете да използвате ArgumentCaptor, за да ги хванете и да извлечете техните стойности за по-нататъшна проверка.