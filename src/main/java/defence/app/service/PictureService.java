package defence.app.service;

import defence.app.model.entity.PictureEntity;

import java.util.List;

public interface PictureService {

    List<String> findAllUrls();

    void savePicture(PictureEntity pictureEntity);
}
