package defence.app.web;
import defence.app.model.bindingModel.CreatePlaceBindingModel;
import defence.app.model.entity.CategoryEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.CategoryEnum;
import defence.app.model.viewModel.PlaceViewModel;
import defence.app.repository.CommentRepository;
import defence.app.repository.PlaceRepository;
import defence.app.service.CategoryService;
import defence.app.service.UserService;
import defence.app.service.impl.PlaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlaceServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private PlaceServiceImpl placeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void testDeletePlace() {
        // Arrange
        Long placeId = 1L;

        // Act
        placeService.deletePlace(placeId);

        // Assert
        verify(commentRepository, times(1)).deleteAllByPlaceId(eq(placeId));
        verify(placeRepository, times(1)).deleteById(eq(placeId));
    }
    @Test
    void testFindPlacesByCategory() {
        // Arrange
        CategoryEntity categoryEntity = new CategoryEntity();
        when(placeRepository.findByCategory(categoryEntity)).thenReturn(List.of(new PlaceEntity()));
        when(modelMapper.map(any(), eq(PlaceViewModel.class))).thenReturn(new PlaceViewModel());

        // Act
        List<PlaceViewModel> places = placeService.findPlacesByCategory(categoryEntity);

        // Assert
        // Add your assertions here
    }

    // Add more tests as needed
}