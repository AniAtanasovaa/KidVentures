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
public class UploadPictureServiceImpl implements UploadPictureService { //Този клас извършва качването на
    // изображение в облачното хранилище Cloudinary и създава съответния обект за представяне на изображението
    // в базата данни.

    //MultipartFile е интерфейс в рамките на Spring Framework, който предоставя стандартизиран начин за
    // представяне на файл, който е получен през HTTP мултипарт (HTTP multipart requests).
    // Този интерфейс е част от пакета org.springframework.web.multipart, и се използва за обработка на файлове,
    // качени от клиентската страна, например чрез HTML форми с enctype="multipart/form-data".
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
//Обяснение на метода:
//Има за цел да качи изображение в Cloudinary, след това да вземе URL-а на каченото изображение и да го съхрани в променливата url:
//Cloudinary.uploader().upload() e метод, предоставен от библиотеката Cloudinary, за качване на изображението.
//Този код използва cloudinary обекта за качване (uploader) и извиква метода upload, който е отговорен за качването
// на изображение в Cloudinary.
// multipartFile.getBytes(): извлича байтовете от MultipartFile, което представлява качения файл, и ги предоставя
// като данни за качване в Cloudinary.
//ObjectUtils.asMap("public_id", UUID.randomUUID().toString()): създава се Map обект, който съдържа параметри
// за качване. В случая, това е public_id, който е уникален идентификатор на изображението в Cloudinary
// и се генерира чрез UUID.randomUUID().toString().
//.get("url"): След като изображението е качено успешно, този метод връща обект, от който се извлича URL
// на каченото изображение. В Cloudinary, след качване на изображение, API отговаря с информация за каченото
// изображение, включително URL-а му.
///.toString(): преобразува URL-а в низов формат (string). Така url става низова променлива,
// която съдържа URL-а на каченото изображение.