package defence.app.service.impl;

import defence.app.model.entity.PictureEntity;
import defence.app.repository.PictureRepository;
import defence.app.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private  final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }


    @Override
    public List<String> findAllUrls() {
        return pictureRepository.findAllUrls();
    }

    @Override
    public void savePicture(PictureEntity pictureEntity) {
        pictureRepository.save(pictureEntity);
    }
}
