package uz.pdp.online.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.dto.ProductDto;
import uz.pdp.online.entity.*;
import uz.pdp.online.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final PropertyRepository propertyRepository;
    private final AttachmentRepository attachmentRepository;

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository, PropertyRepository propertyRepository, AttachmentRepository attachmentRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.propertyRepository = propertyRepository;
        this.attachmentRepository = attachmentRepository;
    }

    List<Property> properties = new ArrayList<>();
    List<Attachment> photos = new ArrayList<>();

    public ResponseEntity<?> addProduct(ProductDto dto) {
        properties.clear();
        photos.clear();
        Product product = new Product();
        Optional<Brand> optionalBrand = brandRepository.findById(dto.getBrandId());
        if (optionalBrand.isEmpty()) return ResponseEntity.status(404).build();

        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()) return ResponseEntity.status(404).build();

        for (Long propertiesId : dto.getPropertiesIds()) {
            Optional<Property> optionalProperty = propertyRepository.findById(propertiesId);
            if (optionalProperty.isEmpty()) return ResponseEntity.status(404).build();
            properties.add(optionalProperty.get());
        }
        for (Long productPhotosId : dto.getProductPhotosIds()) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(productPhotosId);
            if (optionalAttachment.isEmpty()) return ResponseEntity.status(404).build();
            photos.add(optionalAttachment.get());
        }
        product.setName(dto.getName());
        product.setProductPhotos(photos);
        product.setCategory(optionalCategory.get());
        product.setBrand(optionalBrand.get());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setProperties(properties);
        product.setAmount(dto.getAmount());
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) return ResponseEntity.status(404).build();
        return ResponseEntity.ok(optionalProduct.get());
    }

    public ResponseEntity<?> getProducts(int pageNum,int pageSize){
        Pageable pageable= PageRequest.of(pageNum,pageSize);
        return ResponseEntity.ok(productRepository.findAll(pageable));
    }

    public ResponseEntity<?> edit(Long id,ProductDto dto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) return ResponseEntity.status(404).build();
        Product product = optionalProduct.get();

        for (Long propertiesId : dto.getPropertiesIds()) {
            Optional<Property> optionalProperty = propertyRepository.findById(propertiesId);
            if(optionalProperty.isEmpty()) return ResponseEntity.status(404).build();
            properties.add(optionalProperty.get());
        }
        for (Long productPhotosId : dto.getProductPhotosIds()) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(productPhotosId);
            if(optionalAttachment.isEmpty()) return ResponseEntity.status(404).build();
           photos.add(optionalAttachment.get());

        }
        Optional<Brand> optionalBrand = brandRepository.findById(dto.getBrandId());
        if(optionalBrand.isEmpty()) return ResponseEntity.status(404).build();

        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if(optionalCategory.isEmpty()) return ResponseEntity.status(404).build();

        product.setAmount(dto.getAmount());
        product.setName(dto.getName());
        product.setProperties(properties);
        product.setBrand(optionalBrand.get());
        product.setCategory(optionalCategory.get());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setProductPhotos(photos);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) return ResponseEntity.status(404).build();
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
