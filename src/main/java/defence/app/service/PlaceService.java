package defence.app.service;

import defence.app.model.bindingModel.CreatePlaceBindingModel;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.serviceModel.PlaceServiceModel;
import defence.app.model.viewModel.PlaceViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaceService {
    Long addPlace(CreatePlaceBindingModel createPlaceBindingModel, String username);

    void deletePlace(Long id);

    List<PlaceViewModel> findAllPlacesViewModel();

//    Long findById(Long id);

   PlaceViewModel findViewModelById(Long id);

   Page<PlaceViewModel> getAllPlaces(Pageable pageable);

    PlaceEntity getPlaceById(Long placeId);
}

//ToDo pageable лекция Error Handing остава 1.22
