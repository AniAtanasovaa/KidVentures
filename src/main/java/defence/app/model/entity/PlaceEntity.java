package defence.app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "places")
public class PlaceEntity extends BaseEntity {

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false,  columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private UserEntity author;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "place")
    private PictureEntity picture;

    @ManyToOne(cascade = CascadeType.MERGE) // при чочо е с енъма
    private CategoryEntity category;

    public PlaceEntity() {
    }

    public String getCity() {
        return city;
    }

    public PlaceEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public PlaceEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlaceEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PlaceEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public PlaceEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public PlaceEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public PlaceEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }


}
