package defence.app.service.impl;

import defence.app.model.entity.CommentEntity;
import defence.app.model.serviceModel.CommentServiceModel;
import defence.app.model.viewModel.CommentViewModel;
import defence.app.repository.CommentRepository;
import defence.app.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel commentServiceModel) {
        CommentEntity commentEntity = modelMapper.map(commentServiceModel, CommentEntity.class);
        commentRepository.save(commentEntity);

        return modelMapper.map(commentEntity, CommentViewModel.class);
    }

    @Override
    public List<CommentViewModel> getAllCommentsForPlace(Long placeId) {

        List<CommentEntity> comments = commentRepository.findAllByPlaceId(placeId);
        return comments.stream().map(commentEntity -> modelMapper.map(commentEntity,CommentViewModel.class)
                ).collect(Collectors.toList());
    }
}
