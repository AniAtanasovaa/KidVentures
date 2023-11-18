package defence.app.scheduled;

import defence.app.model.entity.DailyStatisticsEntity;
import defence.app.repository.DailyStatisticsRepository;
import defence.app.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DailyUserRegistrationTask {

    private final UserService userService;

    private final DailyStatisticsRepository dailyStatisticsRepository;


    public DailyUserRegistrationTask(UserService userService, DailyStatisticsRepository dailyStatisticsRepository) {
        this.userService = userService;
        this.dailyStatisticsRepository = dailyStatisticsRepository;
    }


    @Scheduled(cron = "0 33 23 * * ?")
    public void recordDailyUserRegistrations() {
        LocalDate today = LocalDate.now();
        long dailyUserRegistrations = userService.countUserRegistrationsForDate(today);

        saveDailyStatistics(today, dailyUserRegistrations);

        // Тук можете да извършите допълнителна логика, като например записване на броя в лог файла или базата данни.
        System.out.println("Daily user registrations on " + today + ": " + dailyUserRegistrations);
    }

    private void saveDailyStatistics(LocalDate date, long dailyUserRegistrations) {


        DailyStatisticsEntity dailyStatistics = dailyStatisticsRepository.findByDate(date);

        if (dailyStatistics == null) {
            dailyStatistics = new DailyStatisticsEntity();
            dailyStatistics.setDate(date);
        }

        dailyStatistics.setDailyRegistrations(dailyUserRegistrations);

        dailyStatisticsRepository.save(dailyStatistics);
    }
    }
