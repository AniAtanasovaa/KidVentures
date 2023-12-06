package defence.app.model.bindingModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationBindingModel { // ще вземе и ще пренесе информацията от формата и ще я валидира


    @NotBlank(message = "Името трябва да съдържа минимум 2 символа")
    @Size(min=2, max=20, message = "Името трябва да е между 2 и 20 символа.") // За да се визуализира съобщението за грешката - в thymeleaf е с th:errors и конкретният field
    private String firstName;
    @NotBlank(message = "Фамилията трябва да съдържа минимум 2 символа")
    @Size(min=2, max=20, message = "Фамилията трябва да е между 2 и 20 символа.")
    private String lastName;
    @NotBlank(message = "Моля, въведете коректно електронната си поща")
    @Email(message = "Моля, въведете коректно електронната си поща")
    private String email;
    @NotBlank(message = "Потребителското име трябва да съдържа минимум 2 символа")
    @Size(min=2, max=20, message = "Потребителското име трябва да е между 2 и 20 символа.")
    private String username;

    @NotBlank(message = "Паролата трябва да съдържа минимум 5 символа")
    @Size(min=2, max=20, message = "Паролата трябва да съдържа минимум 5 символа.")
    private String password;

    @NotBlank(message = "Паролата трябва да съдържа минимум 5 символа")
    @Size(min=2, max=20, message = "Паролата трябва да съдържа минимум 5 символа.")
    private String confirmPassword;

    public UserRegistrationBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
