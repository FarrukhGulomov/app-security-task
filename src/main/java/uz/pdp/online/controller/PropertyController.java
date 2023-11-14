package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.dto.PropertyDto;
import uz.pdp.online.service.ProductService;
import uz.pdp.online.service.PropertyService;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    public final PropertyService propertyService;
    public PropertyController(PropertyService propertyService){
        this.propertyService=propertyService;
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<?> addProperty(@RequestBody PropertyDto dto){
        return propertyService.addProperty(dto);
    }
}
