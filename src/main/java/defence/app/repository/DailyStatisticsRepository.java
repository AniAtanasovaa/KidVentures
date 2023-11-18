package defence.app.repository;

import defence.app.model.entity.DailyStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DailyStatisticsRepository extends JpaRepository<DailyStatisticsEntity, Long> {

    // Метод за взимане на статистика за деня
    DailyStatisticsEntity findByDate(LocalDate date);
}