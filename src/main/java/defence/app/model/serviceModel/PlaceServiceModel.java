package defence.app.model.serviceModel;
import defence.app.model.entity.PictureEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class PlaceServiceModel {

    private Long id;

    @NotBlank
    @Size(min=2, max=20, message = "Името на градът трябва да е между 2 и 20 символа.")
    private String city;
    @NotBlank
    @Size(min=2, max=20, message = "Дължината на адресът трябва да е между 2 и 20 символа.")
    private String address;
    @NotBlank
    @Size(min=2, max=20, message = "Името на мястото трябва да е между 2 и 20 символа.")
    private String name;
    @NotBlank
    @Size(min=2, max=150, message = "Описанието на мястото трябва да е между 2 и 150 символа.")
    private String description;
    private CategoryEnum category;

    private UserEntity author;

    private String imageUrl;

    public PlaceServiceModel() {
    }



    public String getCity() {
        return city;
    }

    public PlaceServiceModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public PlaceServiceModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlaceServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PlaceServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public PlaceServiceModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public Long getId() {
        return id;
    }

    public PlaceServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public PlaceServiceModel setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PlaceServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
