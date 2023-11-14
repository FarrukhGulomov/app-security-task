package uz.pdp.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.dto.CategoryDto;
import uz.pdp.online.entity.Brand;
import uz.pdp.online.entity.Category;
import uz.pdp.online.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> addCategory(CategoryDto dto) {
        boolean existsByName = categoryRepository.existsByName(dto.getName());

        if (!existsByName) {
            Category category = new Category();
            category.setName(dto.getName());
            if (!(dto.getParentCategoryId() == null)) {

                Optional<Category> optionalParentCategory = categoryRepository.findById(dto.getParentCategoryId());
                optionalParentCategory.ifPresent(category::setParentCategory);
            }

            categoryRepository.save(category);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(405).build();

    }

    public ResponseEntity<?> getCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) return ResponseEntity.status(404).build();

        return ResponseEntity.status(200).body(optionalCategory.get());

    }

    public ResponseEntity<?> getCategories(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return ResponseEntity.ok(categoryRepository.findAll(pageable));
    }

    public ResponseEntity<?> editCategory(Long id, CategoryDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category editingCategory = optionalCategory.get();
            boolean existsByNameAndIdNot = categoryRepository.existsByNameAndIdNot(dto.getName(), id);
            if (!existsByNameAndIdNot) {

                if (!(dto.getParentCategoryId() == null)) {
                    Optional<Category> optionalParentCategory = categoryRepository.findById(dto.getParentCategoryId());
                    if(optionalParentCategory.isEmpty()) return ResponseEntity.status(404).build();
                    editingCategory.setParentCategory(optionalParentCategory.get());
                }
                editingCategory.setName(dto.getName());
                categoryRepository.save(editingCategory);
                return ResponseEntity.ok().build();

            }
        }
        return ResponseEntity.status(404).build();


    }

    public ResponseEntity<?> deleteCategory(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()) return ResponseEntity.status(404).build();
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
