package defence.app.model.bindingModel;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePlaceBindingModel {
    @NotBlank(message = "Моля, въведете град.")
    @Size(min=2, max=20, message = "Името на градът трябва да е между 2 и 20 символа.")
    private String city;

    @NotBlank(message = "Моля, въведете адрес.")
    @Size(min=2, max=20, message = "Дължината на адресът трябва да е между 2 и 20 символа.")
    private String address;

    @NotBlank(message = "Моля, въведете име на мястото.")
    @Size(min=2, max=20, message = "Името на мястото трябва да е между 2 и 20 символа.")
    private String name;

    @NotBlank(message = "Моля, въведете описание на мястото.")
    @Size(min=2, max=150, message = "Описанието на мястото трябва да е между 2 и 150 символа.")
    private String description;

    @NotNull(message = "Моля, изберете категория.")
    private CategoryEnum category = CategoryEnum.ВЪТРЕ;
    private UserEntity author;
    private String imageURL;
    public CreatePlaceBindingModel() {
    }
    public CreatePlaceBindingModel(String city, String address, String name, String description,CategoryEnum category, UserEntity author, String imageURL) {
        this.city = city;
        this.address = address;
        this.name = name;
        this.description = description;
        this.category = category;
        this.author = author;
        this.imageURL = imageURL;
    }

    public String getCity() {
        return city;
    }

    public CreatePlaceBindingModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CreatePlaceBindingModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreatePlaceBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreatePlaceBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public CreatePlaceBindingModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CreatePlaceBindingModel setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public CreatePlaceBindingModel setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public static CreatePlaceBindingModel empty() {
        return new CreatePlaceBindingModel(null, null, null,null,CategoryEnum.ВЪТРЕ,null, null);
    }
}
