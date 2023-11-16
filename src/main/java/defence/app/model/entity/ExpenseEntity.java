package defence.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "expenses")
public class ExpenseEntity extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal drinks;
    @Column(nullable = false)
    private BigDecimal food;
    @Column(nullable = false)
    private BigDecimal accommodation;
    private String description;

    @OneToOne
    private PlaceEntity place;

    public ExpenseEntity() {
    }

    public BigDecimal getDrinks() {
        return drinks;
    }

    public ExpenseEntity setDrinks(BigDecimal drinks) {
        this.drinks = drinks;
        return this;
    }

    public BigDecimal getFood() {
        return food;
    }

    public ExpenseEntity setFood(BigDecimal food) {
        this.food = food;
        return this;
    }

    public BigDecimal getAccommodation() {
        return accommodation;
    }

    public ExpenseEntity setAccommodation(BigDecimal accommodation) {
        this.accommodation = accommodation;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public PlaceEntity getPlace() {
        return place;
    }

    public ExpenseEntity setPlace(PlaceEntity place) {
        this.place = place;
        return this;
    }
}
