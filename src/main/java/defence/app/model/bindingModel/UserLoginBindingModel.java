package defence.app.model.bindingModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginBindingModel {

    @NotBlank(message = "Потребителското име трябва да съдържа минимум 2 символа")
    @Size(min=2, max=20, message = "Потребителското име трябва да е между 2 и 20 символа.")
    private String username;

    @NotBlank(message = "Паролата трябва да съдържа минимум 5 символа")
    @Size(min=2, max=20, message = "Паролата трябва да съдържа минимум 5 символа.")
    private String password;
    public UserLoginBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
