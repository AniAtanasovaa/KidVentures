package defence.app.model.bindingModel;
import defence.app.model.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleChangeForm {

    @NotBlank(message = "Потребителското име трябва да съдържа минимум 2 символа")
    @Size(min=2, max=20, message = "Потребителското име трябва да е между 2 и 20 символа.")
    private String username;

    @NotBlank(message = "Моля, изберете роля")
    private RoleEnum newRole;

    public RoleChangeForm() {
    }

    public String getUsername() {
        return username;
    }

    public RoleChangeForm setUsername(String username) {
        this.username = username;
        return this;
    }

    public RoleEnum getNewRole() {
        return newRole;
    }

    public RoleChangeForm setNewRole(RoleEnum newRole) {
        this.newRole = newRole;
        return this;
    }
}
