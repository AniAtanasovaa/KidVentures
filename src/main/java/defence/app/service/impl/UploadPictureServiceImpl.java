package defence.app.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import defence.app.service.PictureService;
import defence.app.service.UploadPictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UploadPictureServiceImpl implements UploadPictureService {

    private final Cloudinary cloudinary;
    private final PictureService pictureService;

    public UploadPictureServiceImpl(Cloudinary cloudinary, PictureService pictureService) {
        this.cloudinary = cloudinary;
        this.pictureService = pictureService;
    }

    @Override
    public String uploadPictureFile(MultipartFile multipartFile) throws IOException {

        String url = cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        ObjectUtils.asMap("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();

        pictureService.createPictureEntity(url);

        return url;
    }
}
