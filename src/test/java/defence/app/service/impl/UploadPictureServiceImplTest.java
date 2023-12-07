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
    private Cloudinary cloudinary; //Cloudinary е облачен сервиз за управление на мултимедийни файлове
    // (изображения, видеа и други). Той предоставя API и инструменти, които позволяват лесно качване,
    // обработка и достъп до мултимедийни съдържания в облака. Uploader e компонент, който улеснява качването
    // на файлове.

    @Mock
    private Uploader uploader; //създавам мок за Uploader, за да гарантирам, че cloudinary.uploader() връща този мок, преди да се извика upload.

    @Mock
    private PictureService pictureService;

    @InjectMocks
    private UploadPictureServiceImpl uploadPictureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(cloudinary.uploader()).thenReturn(uploader); // Конфигурирам връщането на Uploader
    }

    @Test
    void uploadPictureFile_Success() throws IOException {
        // Подготвяне на мокове и заявки
        MultipartFile multipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());


        Map<String, Object> cloudinaryResult = ObjectUtils.asMap("url", "http://example.com/image.jpg");
        // Map<String, Object> cloudinaryResult представлява резултата, който се очаква от Cloudinary след
        // качването на файла. В този случай, се предполага, че качването е успешно и се връща url на качения файл.

        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);
        //Използвайки Mockito, се настройва поведението на uploader.upload() метода. Се казва, че когато се извика
        // с каквито и да е байтове (any(byte[].class)) и каквито и да е параметри (any(Map.class)),
        // трябва да върне предварително зададения cloudinaryResult

        // Извикване на метода, който тестваме
        String result = uploadPictureService.uploadPictureFile(multipartFile);

        // Проверка на резултата
        assertEquals("http://example.com/image.jpg", result);

        // Проверка дали методът pictureService.createPictureEntity() е бил извикан веднъж с правилните параметри
        verify(pictureService, times(1)).createPictureEntity("http://example.com/image.jpg");
    }
}
