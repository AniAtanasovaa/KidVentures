package defence.app.model.viewModel;
import defence.app.model.entity.CategoryEntity;
import defence.app.model.entity.PictureEntity;

public class PlaceViewModel { // този клас ще се използва за показване на съкратена информация за конкретното място, напр.а показване на списък с места в една кратка форма, където е важно да се покаже само основната информация
    Long id;
    private String city;

    private String name;

    private String address;

    private String description;

    private PictureEntity picture;

    private CategoryEntity category;

    public PlaceViewModel(Long id, String city, String name, String address, String description, PictureEntity picture, CategoryEntity category) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.address = address;
        this.description = description;
        this.picture = picture;
        this.category = category;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public PlaceViewModel setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public PlaceViewModel() {
    }

    public Long getId() {
        return id;
    }

    public PlaceViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCity() {
        return city;
    }

    public PlaceViewModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlaceViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PlaceViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public PlaceViewModel setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public PlaceViewModel setAddress(String address) {
        this.address = address;
        return this;
    }


    public String getSummary() {
        return "Име на мястото: " + name + "\nКатегория: " + category.getName().name() + "\nГрад: " + city + "\nАдрес: " + address;
    }
}