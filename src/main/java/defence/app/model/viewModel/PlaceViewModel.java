package defence.app.model.viewModel;
import defence.app.model.entity.CategoryEntity;
import defence.app.model.entity.PictureEntity;

public class PlaceViewModel { // todo този клас ще се използва за показване на съкратена информация за конкретното място, напр.а показване на списък с места в една кратка форма, където е важно да се покаже само основната информация
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


    public String summary() {
        return "Име на мястото: " + name + "\nКатегория: " + category.getName().name() + "\nГрад: " + city + "\nАдрес: " + address;
    }
}

//Todo PlaceDetailsModel: Този модел вероятно съдържа по-подробна информация за мястото. Той включва ID на мястото, име на града, адрес, име на мястото, описание, списък с обекти от тип PictureEntity (предположително изображения, свързани с мястото) и енумерация CategoryEnum, която представлява категорията на мястото (например: ресторант, музей, парк и други).
//
//PlaceViewModel: Този модел може да служи за по-опростено представяне на информацията за мястото. Той съдържа ID на мястото, име на града, име на мястото, описание и URL адрес към снимка, свързана с мястото. Този модел може да се използва, например, за показване на списък с места в една кратка форма, където е важно да се покаже само основната информация без допълнителни подробности.
//
//Тези модели представляват различни нива на детайлност и информация спрямо изискванията на приложението. PlaceDetailsModel е по-подробен, докато PlaceViewModel е по-опростен модел, предназначен да покаже основната информация за мястото.
