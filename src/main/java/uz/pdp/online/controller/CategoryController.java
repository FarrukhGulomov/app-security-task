package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.dto.CategoryDto;
import uz.pdp.online.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto dto){
        return categoryService.addCategory(dto);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id){
        return categoryService.getCategory(id);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam int pageNum,@RequestParam int pageSize){
        return categoryService.getCategories(pageNum,pageSize);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id,@RequestBody CategoryDto dto){
        return categoryService.editCategory(id,dto);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

}
