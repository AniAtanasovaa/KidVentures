package defence.app.model.serviceModel;

public class CommentServiceModel {

   private Long placeId;
   private String content;

   private String author;

    public CommentServiceModel() {
    }

    public Long getPlaceId() {
        return placeId;
    }

    public CommentServiceModel setPlaceId(Long placeId) {
        this.placeId = placeId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentServiceModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommentServiceModel setAuthor(String author) {
        this.author = author;
        return this;
    }
}
