package defence.app.service.impl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

import defence.app.model.entity.CategoryEntity;

import defence.app.model.entity.PlaceEntity;

import defence.app.model.entity.UserEntity;
import defence.app.model.serviceModel.PlaceServiceModel;
import defence.app.model.viewModel.PlaceViewModel;
import defence.app.repositories.PlaceRepository;
import defence.app.service.CategoryService;
import defence.app.service.PictureService;
import defence.app.service.PlaceService;
import defence.app.service.UserService;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;

    private final PictureService pictureService;

    private final UserService userService;
    private final CategoryService categoryService;

    public PlaceServiceImpl(PlaceRepository placeRepository, ModelMapper modelMapper, PictureService pictureService, UserService userService, CategoryService categoryService) {
        this.placeRepository = placeRepository;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

//    @Transactional
//    @Override
//    public Long addPlace(PlaceServiceModel placeServiceModel) {
//
//        PlaceEntity placeEntity = new PlaceEntity();
//
////        pictureEntity.setUrl(placeServiceModel.getPicture());
////
////        placeEntity.setPicture(pictureEntity);
////
////        pictureEntity.setTitle(placeEntity.getPicture().getTitle());
////        pictureEntity.setAuthor(placeEntity.getAuthor());
////        pictureEntity.setPlace(placeEntity);
////
////        pictureService.savePicture(pictureEntity);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        System.out.println(authentication.getPrincipal());
//        System.out.println(authentication.isAuthenticated());
//        System.out.println(authentication.getAuthorities());
//
//        if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
//
//            UserEntity loggedInUser = userService.findFirstByUsername (((UserEntity) authentication.getPrincipal()).getUsername());
//
//            placeEntity.setAuthor(loggedInUser);
//        }
//        placeEntity = modelMapper.map(placeServiceModel, PlaceEntity.class);
//
//            CategoryEntity category = categoryService.findCategoryByName(placeServiceModel.getCategory());
//
//            placeEntity.setCategory(category);
//
//            placeRepository.save(placeEntity);
//
//
//            return placeEntity.getId();
//        }

    @Transactional
    @Override
    public Long addPlace(PlaceServiceModel placeServiceModel, String username) {
        PlaceEntity placeEntity = modelMapper.map(placeServiceModel, PlaceEntity.class);


        UserEntity author = userService.findFirstByUsername(username);
        placeEntity.setAuthor(author);

        CategoryEntity category = categoryService.findCategoryByName(placeServiceModel.getCategory());
        placeEntity.setCategory(category);

        placeRepository.save(placeEntity);

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
//
                        return placeViewModel;
                    })
                    .collect(Collectors.toList());
        }


//    @Transactional
//    @Override
//    public Long findById(Long id) {
//        Optional<PlaceEntity> placeOptional = placeRepository.findById(id);
//        PlaceEntity place = placeOptional.orElseThrow(() -> new EntityNotFoundException("Не съществува място с това ид: " + id));
//        return place.getId();
//    }

    @Override
    public Optional<PlaceViewModel> findViewModelById(Long id) {
        return placeRepository.findById(id).map(
                PlaceServiceImpl::mapToDetails
        );
    }

    @Override
    public Page<PlaceViewModel> getAllPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable).map(PlaceServiceImpl::mapToDetails); // Todo да си променя PalceDetails и да направя метод MapToDetails и даг оползвам тук аз да извличам по - малко информ
    }

    private static PlaceViewModel mapToDetails(PlaceEntity placeEntity) {


        return new PlaceViewModel(
              placeEntity.getId(),
                        placeEntity.getName(),
                        placeEntity.getPicture(),
                placeEntity.getAddress(),
                placeEntity.getCity(),
                placeEntity.getDescription(),
                placeEntity.getCategory()

        );
    }


}

