package defence.app.web;

import defence.app.model.viewModel.PlaceViewModel;
import defence.app.service.PlaceService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/places")
public class PlacesController {

    private final PlaceService placeService;
    private final ModelMapper modelMapper;

    public PlacesController(PlaceService placeService, ModelMapper modelMapper) {
        this.placeService = placeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public String showAllPlaces(Model model, @PageableDefault (size = 12, sort = "id")
                                Pageable pageable) {

        Page<PlaceViewModel> allPlaces = placeService.getAllPlaces(pageable);

        model.addAttribute("places", allPlaces);

        return "places";
    }
}
