package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.dto.ProductDto;
import uz.pdp.online.repository.ProductRepository;
import uz.pdp.online.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDto dto){
        return productService.addProduct(dto);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR','CONSUMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam int pageNum,@RequestParam int pageSize){
        return productService.getProducts(pageNum,pageSize);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody ProductDto dto){
        return productService.edit(id,dto);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return productService.delete(id);
    }

}
