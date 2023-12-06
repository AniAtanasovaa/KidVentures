package defence.app.scheduled;
import defence.app.model.entity.DailyStatisticsEntity;
import defence.app.repository.DailyStatisticsRepository;
import defence.app.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
 //Използвам класа за планиране на задачи в Spring (@Scheduled) за регистриране на статистика за ежедневните
 // регистрации на потребители.
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

        //метода ми маркиран с анотацията @Scheduled ще се изпълнява периодично според зададения cron израз.
        // В случая, израза означава "всеки ден в 23:33".

        LocalDate today = LocalDate.now();
        long dailyUserRegistrations = userService.countUserRegistrationsForDate(today);
        //Извиквам userService.countUserRegistrationsForDate(today), за да получа броят на регистрираните потребители
        // за текущата дата (today).

        saveDailyStatistics(today, dailyUserRegistrations);
        //    Извиква метода saveDailyStatistics(today, dailyUserRegistrations), който записва този брой в базата данни
        //    под формата на DailyStatisticsEntity.

        System.out.println("Брой регистрирани потребители на дата: " + today + ": " + dailyUserRegistrations);
    }

    public void saveDailyStatistics(LocalDate date, long dailyUserRegistrations) {


        DailyStatisticsEntity dailyStatistics = dailyStatisticsRepository.findByDate(date);

        if (dailyStatistics == null) {
            dailyStatistics = new DailyStatisticsEntity();
            dailyStatistics.setDate(date);
        }

        dailyStatistics.setDailyRegistrations(dailyUserRegistrations);

        dailyStatisticsRepository.save(dailyStatistics);
    }
    }
