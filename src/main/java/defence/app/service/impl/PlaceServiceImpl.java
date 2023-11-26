package defence.app.service.impl;
import defence.app.model.bindingModel.CreatePlaceBindingModel;

import defence.app.repository.PictureRepository;
import defence.app.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import defence.app.model.entity.CategoryEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.viewModel.PlaceViewModel;
import defence.app.repository.PlaceRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;


    public PlaceServiceImpl(PlaceRepository placeRepository, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.placeRepository = placeRepository;
        this.modelMapper = modelMapper;

        this.userService = userService;
        this.categoryService = categoryService;

    }

    @Transactional
    @Override
    public Long addPlace(CreatePlaceBindingModel createPlaceBindingModel, String username) {

        PlaceEntity placeEntity = modelMapper.map(createPlaceBindingModel, PlaceEntity.class);

        UserEntity author = userService.findFirstByUsername(username);
        placeEntity.setAuthor(author);

        CategoryEntity category = categoryService.findCategoryByName(createPlaceBindingModel.getCategory());
        placeEntity.setCategory(category);

//      String pictureUrl = createPlaceBindingModel.getImageURL();
//
//       PictureEntity picture = pictureRepository.findByUrl(pictureUrl);
//
//      placeEntity.setPicture(picture);

        placeEntity = placeRepository.saveAndFlush(placeEntity);

        return placeEntity.getId();
    }


    @Override
    @Transactional
    public void deletePlace(Long id) {

        placeRepository.deleteById(id);

    }
    @Transactional
    @Override
    public List<PlaceViewModel> findAllPlacesViewModel() {

            return placeRepository
                    .findAll()
                    .stream()
                    .map(placeEntity -> {
                        PlaceViewModel placeViewModel = modelMapper.map(placeEntity, PlaceViewModel.class);
                        return placeViewModel;
                    })
                    .collect(Collectors.toList());
        }


    @Override
    public PlaceViewModel findViewModelById(Long id) {
        return placeRepository
                .findById(id).map(placeEntity -> modelMapper.map(placeEntity,PlaceViewModel.class)).get();
    }

    @Override
    public Page<PlaceViewModel> getAllPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable)
                .map(placeEntity -> modelMapper.map(placeEntity,PlaceViewModel.class));
    }

    @Override
    public PlaceEntity getPlaceById(Long placeId) {
        return placeRepository.findById(placeId).get();
    }


}

