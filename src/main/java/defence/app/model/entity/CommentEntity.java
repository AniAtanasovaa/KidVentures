package defence.app.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    private boolean approved;

    private LocalDateTime created;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne //от дясната страна е това, което имаме отдолу, като поле
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private PlaceEntity place;

    public CommentEntity() {
    }

    public boolean isApproved() {
        return approved;
    }

    public CommentEntity setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public CommentEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public PlaceEntity getPlace() {
        return place;
    }

    public CommentEntity setPlace(PlaceEntity place) {
        this.place = place;
        return this;
    }
}
