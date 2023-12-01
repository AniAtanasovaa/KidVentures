package defence.app.model.bindingModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewCommentBindingModel {

    @NotBlank
    @Size(min = 10)
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
