package defence.app.web;
import defence.app.model.viewModel.PlaceViewModel;
import defence.app.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlacesControllerTest {

    @Mock
    private PlaceService placeService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlacesController placesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void showAllPlaces_ShouldReturnCorrectViewName() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Page<PlaceViewModel> placesPage = new PageImpl<>(List.of(new PlaceViewModel())); // Mocked page with one item

        when(placeService.getAllPlaces(pageable)).thenReturn(placesPage);

        Model model = mock(Model.class);

        // Act
        String viewName = placesController.showAllPlaces(model, pageable);

        // Assert
        assertEquals("places", viewName);
    }

    @Test
    void showAllPlaces_ShouldAddPlacesToModel() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Page<PlaceViewModel> placesPage = new PageImpl<>(List.of(new PlaceViewModel())); // Mocked page with one item

        when(placeService.getAllPlaces(pageable)).thenReturn(placesPage);

        Model model = mock(Model.class);

        // Act
        placesController.showAllPlaces(model, pageable);

        // Assert
        verify(model, times(1)).addAttribute(eq("places"), eq(placesPage));
    }

    // Add more tests as needed
}
