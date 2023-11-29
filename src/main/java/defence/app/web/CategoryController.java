package defence.app.web;

import defence.app.model.entity.CategoryEntity;
import defence.app.model.enums.CategoryEnum;
import defence.app.model.viewModel.PlaceViewModel;
import defence.app.service.CategoryService;
import defence.app.service.PlaceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final PlaceService placeService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(PlaceService placeService, CategoryService categoryService, ModelMapper modelMapper) {
        this.placeService = placeService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{categoryName}")
    public String showPlacesInCategory(@PathVariable String categoryName, Model model) {
        // Намерете категорията по името
        CategoryEntity category = categoryService.findCategoryByName(CategoryEnum.valueOf(categoryName));

        // Проверка дали категорията съществува
        if (category == null) {

            return "error/404";
        }

        // Намерете всички места в дадената категория
        List<PlaceViewModel> placesInCategory = placeService.findPlacesByCategory(category);

        model.addAttribute("places", placesInCategory);
        model.addAttribute("categoryName", categoryName);

        return "category";
    }

}