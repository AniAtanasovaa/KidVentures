package defence.app.service.impl;
import defence.app.model.entity.PictureEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.repository.PictureRepository;
import defence.app.service.PlaceService;
import defence.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PictureServiceImplTest {

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private PictureServiceImpl pictureService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllUrls() {
        // Мокиране на pictureRepository за да върне стойности при извикване на метода findAllUrls()
        when(pictureRepository.findAllUrls()).thenReturn(Arrays.asList("url1", "url2", "url3"));

        // Извикване на метода, който тестваме
        List<String> result = pictureService.findAllUrls();

        // Проверка дали резултатът е очакваният
        assertEquals(Arrays.asList("url1", "url2", "url3"), result);
    }

    @Test
    void savePicture() {
        // Мокиране на pictureRepository за да не върши нищо при извикване на метода savePicture()
        PictureEntity pictureEntity = new PictureEntity();
        pictureService.savePicture(pictureEntity);

        // Проверка дали методът savePicture() не вика pictureRepository.save()
        verify(pictureRepository, times(1)).save(pictureEntity);
    }

    @Test
    void createPictureEntity() {
        // Извикване на метода, който тестваме
        pictureService.createPictureEntity("testUrl");

        // Проверка дали методът createPictureEntity() вика pictureRepository.save() със създаден PictureEntity
        verify(pictureRepository, times(1)).save(any(PictureEntity.class));
    }

    @Test
    void getPictureEntityByUrl() {
        // Мокиране на pictureRepository за да върне стойност при извикване на метода findByUrl()
        when(pictureRepository.findByUrl("testUrl")).thenReturn(new PictureEntity());

        // Извикване на метода, който тестваме
        PictureEntity result = pictureService.getPictureEntityByUrl("testUrl");

        // Проверка дали резултатът не е null
        assertEquals(PictureEntity.class, result.getClass());
    }

    @Test
    void setPlaceAndAuthor() {
        // Мокиране на placeService за да върне стойност при извикване на метода getPlaceById()
        when(placeService.getPlaceById(1L)).thenReturn(new PlaceEntity());

        // Мокиране на pictureRepository за да върне стойност при извикване на метода findByUrl()
        when(pictureRepository.findByUrl("testUrl")).thenReturn(new PictureEntity());

        when(userService.findFirstByUsername("admin")).thenReturn(new UserEntity());

        // Извикване на метода, който тестваме
        pictureService.setPlaceAndAuthor(1L, "testUrl","admin");

        // Проверка дали методът setPlace() вика pictureRepository.saveAndFlush() със създаден PictureEntity
        verify(pictureRepository, times(1)).saveAndFlush(any(PictureEntity.class));
    }
}