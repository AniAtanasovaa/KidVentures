package defence.app.service;

import defence.app.model.serviceModel.CommentServiceModel;
import defence.app.model.viewModel.CommentViewModel;

import java.util.List;

public interface CommentService {

    CommentViewModel createComment(CommentServiceModel commentServiceModel);

    List<CommentViewModel> getAllCommentsForPlace(Long placeId);
}
