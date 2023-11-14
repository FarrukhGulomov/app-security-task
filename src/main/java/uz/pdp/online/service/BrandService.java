package uz.pdp.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.entity.Brand;
import uz.pdp.online.repository.BrandRepository;

import java.util.Optional;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public ResponseEntity<?> addBrand(Brand brand){
        boolean existsByName = brandRepository.existsByName(brand.getName());

        if(!existsByName) {
            brandRepository.save(brand);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(405).build();

    }
    public ResponseEntity<?> getBrand(Long id){
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if(optionalBrand.isEmpty()) return ResponseEntity.status(404).build();
    return ResponseEntity.status(202).body(optionalBrand.get());
    }

    public ResponseEntity<?> getBrands(int pageNumber,int pageSize){
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        return ResponseEntity.ok(brandRepository.findAll(pageable));
    }

    public ResponseEntity<?> editBrand(Long id,Brand brand){
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if(optionalBrand.isEmpty()) return ResponseEntity.status(404).build();

        boolean existsByNameAndIdNot = brandRepository.existsByNameAndIdNot(brand.getName(), id);
        if(existsByNameAndIdNot) return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();

        Brand editingBrand = optionalBrand.get();
        editingBrand.setName(brand.getName());
        brandRepository.save(editingBrand);
        return ResponseEntity.ok().build();

    }

    public ResponseEntity<?> deleteBrand(Long id){
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if(optionalBrand.isEmpty()) return ResponseEntity.status(404).build();

        brandRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
