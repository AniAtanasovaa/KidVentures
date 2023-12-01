package defence.app.model.viewModel;

import java.time.LocalDateTime;

public class CommentViewModel {

    private Long id;

    private String content;

    private String author;

    private LocalDateTime created;

    private  boolean canApprove;

    private  boolean canDelete;

    public CommentViewModel() {
    }

    public Long getId() {
        return id;
    }

    public CommentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommentViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public CommentViewModel setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public boolean isCanApprove() {
        return canApprove;
    }

    public CommentViewModel setCanApprove(boolean canApprove) {
        this.canApprove = canApprove;
        return this;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public CommentViewModel setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
        return this;
    }
}
