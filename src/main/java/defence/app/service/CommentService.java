package defence.app.service;

import defence.app.model.bindingModel.NewCommentBindingModel;

import defence.app.model.entity.CommentEntity;
import defence.app.model.viewModel.CommentViewModel;

import java.util.List;

public interface CommentService {

    CommentEntity createComment(NewCommentBindingModel newCommentBindingModel, String username, Long id);

    List<CommentViewModel> getAllCommentsForPlace(Long placeId);
    void createDefaultComment(Long placeId);
    void deleteComment(Long commentId);


}
