package defence.app.service.impl;
import defence.app.model.bindingModel.NewCommentBindingModel;
import defence.app.model.entity.CommentEntity;
import defence.app.model.entity.PlaceEntity;
import defence.app.model.entity.UserEntity;
import defence.app.model.viewModel.CommentViewModel;
import defence.app.repository.*;
import defence.app.service.CategoryService;
import defence.app.service.CommentService;
import defence.app.service.PlaceService;
import defence.app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private CommentService commentService;

    private CommentEntity comment;
    @Mock
    private CommentRepository testCommentRepository;
    private ModelMapper modelMapper;
    @Mock
    private UserService testUserService;
    @Mock
    private PlaceService testPlaceService;

    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        testCommentRepository = mock(CommentRepository.class); // Инициализацирам с Mockito
        commentService = new CommentServiceImpl(testCommentRepository, modelMapper, testUserService, testPlaceService);
        testUserService = new UserServiceImpl(userRepository, passwordEncoder, roleRepository, modelMapper, applicationEventPublisher);
        testPlaceService = new PlaceServiceImpl(placeRepository, modelMapper, testUserService, categoryService, testCommentRepository);
        categoryService = new CategoryServiceImpl(categoryRepository);

        // Инициализирам валиден CommentEntity
        comment = new CommentEntity();
        comment.setContent("Примерно съдържание");

        comment.setPlace(new PlaceEntity());
        comment.setAuthor(new UserEntity());
    }

    @Test
    void createComment() {
        // Arrange
        NewCommentBindingModel newCommentBindingModel = new NewCommentBindingModel();
        String content = "Content";
        newCommentBindingModel.setContent(content);

        CommentEntity savedComment = new CommentEntity();
        savedComment.setPlace(new PlaceEntity());
        savedComment.setAuthor(new UserEntity());
        savedComment.setCreated(LocalDateTime.now());
        savedComment.setContent(content);

        // Act
        commentService.createComment(newCommentBindingModel, "Ani", 1L);

        // Assert
        ArgumentCaptor<CommentEntity> commentEntityArgumentCaptor = ArgumentCaptor.forClass(CommentEntity.class);
        //създава ArgumentCaptor, който е част от библиотеката Mockito. Този обект се използва за хващане (capturing)
        // на аргументите, които са предадени при извикването на метод, който се мокира (mock) или стъбва (stub)
        // с Mockito.

        // Проверявам само за извикване на save метода на репозиторито
        verify(testCommentRepository, times(1)).save(any(CommentEntity.class));

        //В конкретния случай, коментара се създава чрез commentService.createComment(newCommentBindingModel, "Ani", 1L)
        // Тъй като репозиторито (testCommentRepository) е мокнато, се очаква да се извика метода save на репозиторито
        // с параметър от тип CommentEntity.
        //ArgumentCaptor се използва след това, за да хване (capturing) този параметър и да може да се направят допълнителни проверки върху него.

        // Извличам аргумента, подаден на save метода, за допълнителни проверки
        verify(testCommentRepository, times(1)).save(commentEntityArgumentCaptor.capture());
        CommentEntity capturedComment = commentEntityArgumentCaptor.getValue();

        assertThat(capturedComment.getContent()).isEqualTo(savedComment.getContent());

    }

    @Test
    void getAllCommentsForPlace() {
        when(testCommentRepository.findAllByPlaceId(1L)).thenReturn(List.of(comment));

        List<CommentViewModel> comments = commentService.getAllCommentsForPlace(1L)
                .stream().map(comment -> modelMapper.map(comment, CommentViewModel.class)).toList();

        Assertions.assertEquals(comments.size(), 1);
    }

    @Test
    void createDefaultComment() {

        NewCommentBindingModel newCommentBindingModel = new NewCommentBindingModel();

        String content = "Чудесно място за забавление за деца";

        newCommentBindingModel.setContent(content);

        Long placeDefaultId = 1L;

        String usernameForDefaultComment = "default";

        commentService.createDefaultComment(1L);

        // Assert
        ArgumentCaptor<CommentEntity> commentEntityArgumentCaptor = ArgumentCaptor.forClass(CommentEntity.class);

        // Проверявам само за извикване на save метода на репозиторито
        verify(testCommentRepository, times(1)).save(any(CommentEntity.class));

        // Извличам аргумента, подаден на save метода, за допълнителни проверки
        verify(testCommentRepository, times(1)).save(commentEntityArgumentCaptor.capture());
        CommentEntity capturedComment = commentEntityArgumentCaptor.getValue();

        assertThat(capturedComment.getContent()).isEqualTo(newCommentBindingModel.getContent());

    }
}
