package defence.app.service.impl;

import defence.app.model.bindingModel.NewCommentBindingModel;
import defence.app.model.entity.CommentEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.viewModel.CommentViewModel;
import defence.app.repository.CommentRepository;
import defence.app.service.CommentService;
import defence.app.service.PlaceService;
import defence.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    private final UserService userService;

    private final PlaceService placeService;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, UserService userService, PlaceService placeService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.placeService = placeService;
    }

    @Override
    public CommentEntity createComment(NewCommentBindingModel newCommentBindingModel, String username, Long id) {

        CommentEntity commentEntity = modelMapper.map(newCommentBindingModel, CommentEntity.class);
        commentEntity.setApproved(false);
        commentEntity.setCreated(LocalDateTime.now());
        commentEntity.setContent(newCommentBindingModel.getContent());
        PlaceEntity place = placeService.getPlaceById(id);
        commentEntity.setPlace(place);
        UserEntity author = userService.findFirstByUsername(username);
        commentEntity.setAuthor(author);

        return commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentViewModel> getAllCommentsForPlace(Long placeId) {

        List<CommentEntity> comments = commentRepository.findAllByPlaceId(placeId);
        return comments.stream().map(commentEntity -> modelMapper.map(commentEntity,CommentViewModel.class)
                ).collect(Collectors.toList());
    }

    public void createDefaultComment(Long placeId) {
        // Създаване на коментар по подразбиране
        NewCommentBindingModel defaultComment = new NewCommentBindingModel();
        defaultComment.setContent(".");

        // Заместете "admin" със съществуващо потребителско име в системата
        String defaultUsername = "admin";

        // Създаване на коментара в базата данни
        createComment(defaultComment, defaultUsername, placeId);
    }
}

