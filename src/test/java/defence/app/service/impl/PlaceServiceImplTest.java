package defence.app.service.impl;


import defence.app.model.bindingModel.CreatePlaceBindingModel;
import defence.app.model.entity.CategoryEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.enums.CategoryEnum;
import defence.app.model.viewModel.PlaceViewModel;
import defence.app.repository.CategoryRepository;
import defence.app.repository.CommentRepository;
import defence.app.repository.PlaceRepository;
import defence.app.service.CategoryService;
import defence.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlaceServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private PlaceServiceImpl placeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    } //метода инициализира mock обекти, които са анотирани с @Mock и инжектира тези mock обекти в тестовия клас.

    @Test
    void addPlace() {
        CreatePlaceBindingModel createPlaceBindingModel = new CreatePlaceBindingModel();
        createPlaceBindingModel.setName("Test Place");
        createPlaceBindingModel.setCategory(CategoryEnum.НАВЪН);

        UserEntity author = new UserEntity();
        when(userService.findFirstByUsername(anyString())).thenReturn(author);

        CategoryEntity category = new CategoryEntity();
        when(categoryRepository.findByName(CategoryEnum.НАВЪН)).thenReturn(Optional.of(category));

        PlaceEntity placeEntity = new PlaceEntity();
        when(modelMapper.map(createPlaceBindingModel, PlaceEntity.class)).thenReturn(placeEntity);

        when(placeRepository.saveAndFlush(placeEntity)).thenReturn(placeEntity);

        // Извикване на метода, който тестваме
        Long result = placeService.addPlace(createPlaceBindingModel, "testUser");

        // Проверка дали методът addPlace() връща очакваният резултат
        assertEquals(placeEntity.getId(), result);
    }

    @Test
    void deletePlace() {
        Long placeId = 1L;

        // Мокиране на commentRepository и placeRepository
        doNothing().when(commentRepository).deleteAllByPlaceId(placeId);
        doNothing().when(placeRepository).deleteById(placeId);

        // Извикване на метода, който тестваме
        placeService.deletePlace(placeId);

        // Проверка дали методът deletePlace() вика commentRepository.deleteAllByPlaceId() и placeRepository.deleteById()
        verify(commentRepository, times(1)).deleteAllByPlaceId(placeId);
        verify(placeRepository, times(1)).deleteById(placeId);
    }

    @Test
    void findAllPlacesViewModel() {
        // Мокиране на placeRepository за да върне стойности при извикване на метода findAll()
        when(placeRepository.findAll()).thenReturn(Arrays.asList(new PlaceEntity(), new PlaceEntity()));

        // Мокиране на modelMapper за да върне стойности при извикване на метода map()
        when(modelMapper.map(any(), eq(PlaceViewModel.class))).thenReturn(new PlaceViewModel());
        // при извикване на метода map на modelMapper с каквито и да било аргументи (с any()), и втория аргумент,
        // който е от тип PlaceViewModel.class (с eq(PlaceViewModel.class)), ще бъде върнат нов обект от тип PlaceViewModel.

        // Извикване на метода, който тестваме
        List<PlaceViewModel> result = placeService.findAllPlacesViewModel();

        // Проверка дали методът findAllPlacesViewModel() връща очакваният брой резултати
        assertEquals(2, result.size());
    }

    @Test
    void findViewModelById() {
        Long placeId = 1L;
        PlaceEntity placeEntity = new PlaceEntity();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(placeEntity));

        when(modelMapper.map(placeEntity, PlaceViewModel.class)).thenReturn(new PlaceViewModel());

        // Извикване на метода, който тестваме
        PlaceViewModel result = placeService.findViewModelById(placeId);

        // Проверка дали методът findViewModelById() връща очакваният резултат
        assertEquals(PlaceViewModel.class, result.getClass());
    }

    @Test
    void getAllPlaces() {

        Pageable pageable = mock(Pageable.class); //Създавам мок обект на интерфейса Pageable
        when(placeRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(new PlaceEntity(), new PlaceEntity())));
//Задава се поведението на placeRepository, когато се извика методът findAll с предоставения pageable.
// Се очаква да бъде върнат обект от тип PageImpl, съдържащ два елемента (Arrays.asList(new PlaceEntity(), new PlaceEntity())).

        when(modelMapper.map(any(), eq(PlaceViewModel.class))).thenReturn(new PlaceViewModel());

        // Извикване на метода, който тестваме
        Page<PlaceViewModel> result = placeService.getAllPlaces(pageable);

        // Проверка дали методът getAllPlaces() връща очакваният брой резултати
        assertEquals(2, result.getContent().size());
    }

    @Test
    void getPlaceById() {

        Long placeId = 1L;
        PlaceEntity placeEntity = new PlaceEntity();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(placeEntity));

        // Извикване на метода, който тестваме
        PlaceEntity result = placeService.getPlaceById(placeId);

        // Проверка дали методът getPlaceById() връща очакваният резултат
        assertEquals(placeEntity, result);
    }
}

//Мокирането се извършва чрез библиотеката Mockito и цели да подмени реалния обект (в случая modelMapper)
// с мок обект, който има предопределено поведение.