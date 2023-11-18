package defence.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "daily_statistics")
public class DailyStatisticsEntity extends BaseEntity {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "daily_registrations")
    private long dailyRegistrations;

    public DailyStatisticsEntity() {
    }

    public LocalDate getDate() {
        return date;
    }

    public DailyStatisticsEntity setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public long getDailyRegistrations() {
        return dailyRegistrations;
    }

    public DailyStatisticsEntity setDailyRegistrations(long dailyRegistrations) {
        this.dailyRegistrations = dailyRegistrations;
        return this;
    }
}