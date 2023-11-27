package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.dto.PropertyDto;
import uz.pdp.online.entity.Property;
import uz.pdp.online.service.CharacteristicService;
import uz.pdp.online.service.PropertyService;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    public final PropertyService propertyService;
    public PropertyController(PropertyService propertyService){
        this.propertyService=propertyService;
    }

//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR','CUSTOMER')")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> get(@PathVariable Long id){
//        return propertyService(id);
//    }
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
//    @GetMapping
//    public ResponseEntity<?> getProperties(@RequestParam int pageNumber,@RequestParam int pageSize){
//        return propertyService.getProperty(pageNumber,pageSize);
//    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody PropertyDto dto){
        return propertyService.add(dto);
    }

//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    @PutMapping("/{id}")
//    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody Property property){
//        return propertyService.editProperty(id,property);
//    }
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id){
//        return propertyService.deleteProperty(id);
//    }
}
