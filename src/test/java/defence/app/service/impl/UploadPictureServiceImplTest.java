package defence.app.service.impl;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import defence.app.service.PictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UploadPictureServiceImplTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader; //създавам мок за Uploader, за да гарантирам, че cloudinary.uploader() връща този мок, преди да се извика upload.

    @Mock
    private PictureService pictureService;

    @InjectMocks
    private UploadPictureServiceImpl uploadPictureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(cloudinary.uploader()).thenReturn(uploader); // Конфигурирайте връщането на Uploader
    }

    @Test
    void uploadPictureFile_Success() throws IOException {
        // Подготвяне на мокове и заявки
        MultipartFile multipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        // Очакван резултат от Cloudinary
        Map<String, Object> cloudinaryResult = ObjectUtils.asMap("url", "http://example.com/image.jpg");

        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // Извикване на метода, който тестваме
        String result = uploadPictureService.uploadPictureFile(multipartFile);

        // Проверка на резултата
        assertEquals("http://example.com/image.jpg", result);

        // Проверка дали методът pictureService.createPictureEntity() е бил извикан веднъж с правилните параметри
        verify(pictureService, times(1)).createPictureEntity("http://example.com/image.jpg");
    }
}
