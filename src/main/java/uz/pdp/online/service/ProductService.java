package uz.pdp.online.service;

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
    private final CharacteristicRepository characteristicRepository;
    private final AttachmentRepository attachmentRepository;

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository, CharacteristicRepository characteristicRepository, AttachmentRepository attachmentRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.characteristicRepository = characteristicRepository;
        this.attachmentRepository = attachmentRepository;
    }

    List<Characteristic> characteristics = new ArrayList<>();
    List<Attachment> photos = new ArrayList<>();

    public ResponseEntity<?> addProduct(ProductDto dto) {
        characteristics.clear();
        photos.clear();
        Product product = new Product();
        Optional<Brand> optionalBrand = brandRepository.findById(dto.getBrandId());
        if (optionalBrand.isEmpty()) return ResponseEntity.status(404).build();

        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()) return ResponseEntity.status(404).build();

        for (Long characteristicsId : dto.getCharacteristicsIds()) {
            Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(characteristicsId);
            if (optionalCharacteristic.isEmpty()) return ResponseEntity.status(404).build();
            characteristics.add(optionalCharacteristic.get());
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
        product.setCharacteristics(characteristics);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }
}
