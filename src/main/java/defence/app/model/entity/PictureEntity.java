package defence.app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="pictures")
public class PictureEntity extends BaseEntity{
    @Column(nullable = false)
    private String url;
    @ManyToOne
    private UserEntity author;
    @OneToOne
    private PlaceEntity place;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public PictureEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public PlaceEntity getPlace() {
        return place;
    }

    public PictureEntity setPlace(PlaceEntity place) {
        this.place = place;
        return this;
    }
}