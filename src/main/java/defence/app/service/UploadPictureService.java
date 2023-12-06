package defence.app.service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface UploadPictureService {

    String uploadPictureFile(MultipartFile multipartFile) throws IOException;

    //Метода приема параметър от тип MultipartFile, който е обект, представляващ файл, който ще бъде качен
}
