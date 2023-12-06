package defence.app.model.bindingModel;
import jakarta.validation.constraints.NotEmpty;
public class NewCommentBindingModel {

@NotEmpty
    String content;

    public NewCommentBindingModel() {
    }

    public String getContent() {
        return content;
    }

    public NewCommentBindingModel setContent(String content) {
        this.content = content;
        return this;
    }
}
