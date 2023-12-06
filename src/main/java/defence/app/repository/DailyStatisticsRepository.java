package defence.app.repository;
import defence.app.model.entity.DailyStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository // Използвам го в  class DailyUserRegistrationTask за планиране на задачи в Spring (@Scheduled) за регистриране на статистика за ежедневните
// регистрации на потребители.
public interface DailyStatisticsRepository extends JpaRepository<DailyStatisticsEntity, Long> {

    // Метод за взимане на статистиката за деня
    DailyStatisticsEntity findByDate(LocalDate date);
}