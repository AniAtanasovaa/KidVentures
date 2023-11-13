package defence.app.service;

import defence.app.model.serviceModel.PlaceServiceModel;
import defence.app.model.viewModel.PlaceViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlaceService {
    Long addPlace(PlaceServiceModel placeServiceModel,String username);

    void deletePlace(Long id);

    List<PlaceViewModel> findAllPlacesViewModel();

//    Long findById(Long id);

   Optional<PlaceViewModel> findViewModelById(Long id);

   Page<PlaceViewModel> getAllPlaces(Pageable pageable);

}

//ToDo pageable лекция Error Handing остава 1.22
