package defence.app.model.viewModel;

import defence.app.model.entity.PictureEntity;
import defence.app.model.enums.CategoryEnum;

import java.util.List;

public class PlaceDetailsModel { // todo този клас ще се използва за показване на детайлна информация за конкретното място по ид

   Long id;
    private String city;

    private String address;

    private String name;

    private String description;

    private String pictureUrl;

    private CategoryEnum category;

    public PlaceDetailsModel() {
    }


    public Long getId() {
        return id;
    }

    public PlaceDetailsModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCity() {
        return city;
    }

    public PlaceDetailsModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public PlaceDetailsModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlaceDetailsModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PlaceDetailsModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public PlaceDetailsModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public PlaceDetailsModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public String summary () {

        return name + " " + city + " " + address + " " + pictureUrl;
    }
}
//Todo PlaceDetailsModel: Този модел вероятно съдържа по-подробна информация за мястото. Той включва ID на мястото, име на града, адрес, име на мястото, описание, списък с обекти от тип PictureEntity (предположително изображения, свързани с мястото) и енумерация CategoryEnum, която представлява категорията на мястото (например: ресторант, музей, парк и други).
//
//PlaceViewModel: Този модел може да служи за по-опростено представяне на информацията за мястото. Той съдържа ID на мястото, име на града, име на мястото, описание и URL адрес към снимка, свързана с мястото. Този модел може да се използва, например, за показване на списък с места в една кратка форма, където е важно да се покаже само основната информация без допълнителни подробности.
//
//Тези модели представляват различни нива на детайлност и информация спрямо изискванията на приложението. PlaceDetailsModel е по-подробен, докато PlaceViewModel е по-опростен модел, предназначен да покаже основната информация за мястото.
