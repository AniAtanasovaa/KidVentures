package defence.app.service;

import defence.app.model.entity.CategoryEntity;
import defence.app.model.enums.CategoryEnum;

import java.util.Optional;

public interface CategoryService {

    CategoryEntity findCategoryByName(CategoryEnum categoryEnum);

    Optional<CategoryEntity> findById(Long id);
    void initCategories();


}
