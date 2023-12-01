package defence.app.web;

import defence.app.model.viewModel.CommentViewModel;
import defence.app.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/place/{placeId}")

    public List<CommentViewModel> getAllCommentsForPlace(@PathVariable Long placeId) {
        return commentService.getAllCommentsForPlace(placeId);
    }
}
