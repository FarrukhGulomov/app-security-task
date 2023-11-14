package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Brand;
import uz.pdp.online.service.BrandService;

@RestController
@RequestMapping("/api/brands")
public class BrandController{
    private final BrandService brandService;
    public BrandController(BrandService brandService){
        this.brandService=brandService;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBrand(@PathVariable Long id){
        return brandService.getBrand(id);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','OPERATOR','MODERATOR')")
    @GetMapping
    public ResponseEntity<?> getBrands(@RequestParam int pageNumber,@RequestParam int pageSize){
        return brandService.getBrands(pageNumber,pageSize);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<?> addBrand(@RequestBody Brand brand){
        return brandService.addBrand(brand);
    }





    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editBrand(@PathVariable Long id,@RequestBody Brand brand){
        return brandService.editBrand(id,brand);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id){
        return brandService.deleteBrand(id);
    }
}
