package defence.app.service;

import defence.app.model.entity.CategoryEntity;
import defence.app.model.enums.CategoryEnum;
import defence.app.repository.CategoryRepository;

import defence.app.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository testCategoryRepository;

    private CategoryService testCategoryService;

    @BeforeEach
    void setUp() {
        testCategoryService = new CategoryServiceImpl(testCategoryRepository);
    }

    @Test
    void testFindAllCategories() {

        // Arrange - подговотка на тестовите данни т.е. в случая са 1 нов List<CategoryEntity>
        List<CategoryEntity> categories = List.of(
                new CategoryEntity(),
                new CategoryEntity(),
                new CategoryEntity()

        );

        when(testCategoryRepository.findAll()).thenReturn(categories);

        // Act - Взимаме това, което тестваме
        List<CategoryEntity> result = testCategoryRepository.findAll();

        // Assert - Първи аргумент - това, което очакваме,
        // 2ри  аргумент - това, което реално е дошло, 3ти  аргумент (опционален) - съобщение за грешка

        assertEquals(categories.size(), result.size());
        verify(testCategoryRepository, times(1)).findAll();

        //verify: Този метод от Mockito се използва за проверка дали дадено действие (например, извикване на метод) се е случило. В случая, той проверява дали методът findAll() на categoryRepository е извикан.
// categoryRepository: Това е mock обект на CategoryRepository. Mock обекти се използват в тестове, за да заменят реални обекти и да се контролира тяхното поведение.
//
//times(1): Този метод указва колко пъти очакваме даденото действие да бъде извършено. В случая, times(1) означава, че очакваме findAll() методът да бъде извикан точно веднъж.
//
//След като този ред от тестовия код се изпълни успешно, можем да бъдем сигурни, че методът findAll() на categoryRepository е бил извикан точно веднъж, както сме очаквали. Това е важно за тестовете, защото искаме да се уверим, че методите се извикват с правилните параметри и брой пъти, което е част от желаното поведение на системата.

    }

    @Test
    void testFindCategoryByNameExistingCategory() {
        // Arrange
        CategoryEntity outside = new CategoryEntity().setName(CategoryEnum.НАВЪН);

        // Направете mock за резултата на findByName да връща outside
        when(testCategoryRepository.findByName(CategoryEnum.НАВЪН)).thenReturn(Optional.of(outside));

        // Act
        Optional<CategoryEntity> result = Optional.ofNullable(testCategoryService.findCategoryByName(CategoryEnum.НАВЪН));

        // Assert
        assertNotNull(result);
        assertEquals(CategoryEnum.НАВЪН, result.get().getName());
        verify(testCategoryRepository, times(1)).findByName(CategoryEnum.НАВЪН);
    }

    @Test
    void testFindCategoryByIdExistingCategory() {
        // Arrange
        CategoryEntity testCategory = new CategoryEntity();
        testCategory.setId(1L);


        when(testCategoryService.findById(1L)).thenReturn(Optional.of(testCategory));

        // Act
        Optional<CategoryEntity> result = testCategoryService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.get().getId());
        verify(testCategoryRepository, times(1)).findById(1L);
    }

}
