package defence.app.service.impl;

import defence.app.model.entity.CategoryEntity;
import defence.app.model.enums.CategoryEnum;
import defence.app.repositories.CategoryRepository;
import defence.app.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CategoryEntity findCategoryByName(CategoryEnum categoryEnum) {
        return categoryRepository.findByName(categoryEnum).orElse(null);
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void initCategories() {

        if(categoryRepository.count()!=0){
            return;
        }

        Arrays.stream(CategoryEnum.values()).forEach(categoryEnum -> {
            CategoryEntity categoryEntity = new CategoryEntity();
           categoryEntity.setName(categoryEnum);

            switch (categoryEnum) {

                case НАВЪН -> categoryEntity.setDescription("Места на открито");
                case ВЪТРЕ -> categoryEntity.setDescription("Места на закрито");
                case ЖИВОТНИ -> categoryEntity.setDescription("Места, в които могат да се срещнат животни");
                case DEFAULT_CATEGORY -> categoryEntity.setDescription("Дефолтна категория - обхваща всички места");


            }

            categoryRepository.save(categoryEntity);
        });
    }



}
