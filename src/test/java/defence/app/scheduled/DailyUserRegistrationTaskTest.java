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

    @Mock
    private UserService userService;

    @Mock
    private DailyStatisticsRepository dailyStatisticsRepository;

    @InjectMocks
    private DailyUserRegistrationTask dailyUserRegistrationTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void recordDailyUserRegistrations() {
        LocalDate today = LocalDate.now();
        long dailyUserRegistrations = 10L;

        // Подготвяме мокове и заявки
        when(userService.countUserRegistrationsForDate(today)).thenReturn(dailyUserRegistrations);
        dailyUserRegistrationTask.recordDailyUserRegistrations();

        // Хващаме аргумента, който се подава на save метода
        ArgumentCaptor<DailyStatisticsEntity> captor = ArgumentCaptor.forClass(DailyStatisticsEntity.class);
        verify(dailyStatisticsRepository, times(1)).save(captor.capture());

        // Проверяваме дали аргументът има правилните стойности
        DailyStatisticsEntity capturedEntity = captor.getValue();
        assertEquals(today, capturedEntity.getDate());
        assertEquals(dailyUserRegistrations, capturedEntity.getDailyRegistrations());
    }
    @Test
    void saveDailyStatistics() {
        LocalDate date = LocalDate.now();
        long dailyUserRegistrations = 5L;

        // Подготвяме мокове и заявки
        DailyStatisticsEntity existingDailyStatistics = new DailyStatisticsEntity();
        existingDailyStatistics.setDate(date);

        when(dailyStatisticsRepository.findByDate(date)).thenReturn(existingDailyStatistics);

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