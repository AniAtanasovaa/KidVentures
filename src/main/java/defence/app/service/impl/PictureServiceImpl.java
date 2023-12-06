package defence.app.service.impl;

import defence.app.model.entity.PictureEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.repository.PictureRepository;
import defence.app.service.PictureService;
import defence.app.service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final PlaceService placeService;

    public PictureServiceImpl(PictureRepository pictureRepository, PlaceService placeService) {
        this.pictureRepository = pictureRepository;
        this.placeService = placeService;
    }


    @Override
    public List<String> findAllUrls() {
        return pictureRepository.findAllUrls();
    }

    @Override
    public void savePicture(PictureEntity pictureEntity) {
        pictureRepository.save(pictureEntity);
    }

    @Override
    public void createPictureEntity(String pictureUrl) {
        PictureEntity picture = new PictureEntity();
        picture.setUrl(pictureUrl);
        pictureRepository.save(picture);
    }

    @Override
    public PictureEntity getPictureEntityByUrl(String pictureUrl) {
        return pictureRepository.findByUrl(pictureUrl);
    }

    @Override
    public void setPlace(Long placeId, String pictureUrl) {
        PlaceEntity placeEntity = placeService.getPlaceById(placeId);

        PictureEntity pictureEntity = pictureRepository.findByUrl(pictureUrl);

        pictureEntity.setPlace(placeEntity);

        pictureRepository.saveAndFlush(pictureEntity);
    }
}