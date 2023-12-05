package defence.app.web;
import defence.app.model.entity.CategoryEntity;
import defence.app.model.enums.CategoryEnum;
import defence.app.model.viewModel.PlaceViewModel;
import defence.app.service.CategoryService;
import defence.app.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryControllerTest {

    @Mock
    private PlaceService placeService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowPlacesInCategoryWithExistingCategory() {
        // Arrange
        String categoryName = "НАВЪН";
        CategoryEntity categoryEntity = new CategoryEntity();
        List<PlaceViewModel> placesInCategory = new ArrayList<>();
        when(categoryService.findCategoryByName(CategoryEnum.НАВЪН)).thenReturn(categoryEntity);
        when(placeService.findPlacesByCategory(categoryEntity)).thenReturn(placesInCategory);

        // Act
        String viewName = categoryController.showPlacesInCategory(categoryName, model);

        // Assert
        assertEquals("category", viewName);
        verify(model).addAttribute("placesInCategory", placesInCategory);
        verify(model).addAttribute("categoryName", categoryName);
    }

    @Test
    void testShowPlacesInCategoryWithNonExistingCategory() {
        // Arrange
        String categoryName = "НАВЪН";
        when(categoryService.findCategoryByName(CategoryEnum.НАВЪН)).thenReturn(null);

        // Act
        String viewName = categoryController.showPlacesInCategory(categoryName, model);

        // Assert
        assertEquals("error/404", viewName);
        // Можете да добавите и други проверки, ако се налага
    }


}