package defence.app.model.entity;

import defence.app.model.enums.CategoryEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum name;

    @Column()
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private List<PlaceEntity> place;

    public CategoryEntity() {
    }

    public CategoryEnum getName() {
        return name;
    }

    public CategoryEntity setName(CategoryEnum name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<PlaceEntity> getPlace() {
        return place;
    }

    public CategoryEntity setPlace(List<PlaceEntity> place) {
        this.place = place;
        return this;
    }
}
