package defence.app.service;

import defence.app.model.entity.PictureEntity;

import java.util.List;

public interface PictureService {

    List<String> findAllUrls();

    void savePicture(PictureEntity pictureEntity);

    void createPictureEntity(String pictureUrl);

    PictureEntity getPictureEntityByUrl(String pictureUrl);

    void setPlace(Long placeId, String pictureUrl);
}
