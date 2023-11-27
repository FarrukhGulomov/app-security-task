package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.dto.PropertyDto;
import uz.pdp.online.entity.Characteristic;
import uz.pdp.online.service.CharacteristicService;
import uz.pdp.online.service.PropertyService;

@RestController
@RequestMapping("/api/characteristics")
public class CharacteristicController {
    public final CharacteristicService characteristicService;
    public CharacteristicController(CharacteristicService characteristicService){
       this.characteristicService=characteristicService;
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Characteristic characteristic){
        return characteristicService.add(characteristic);
    }
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
//    @GetMapping
//    public ResponseEntity<?> getAll(@RequestParam int pageNum,@RequestParam int pageSize){
//        return propertyService.getCharacteristics(pageNum,pageSize);
//    }
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
//    @PutMapping("/{id}")
//    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody PropertyDto dto){
//        return propertyService.editCharacteristic(id,dto);
//    }
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR','CUSTOMER')")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> get(@PathVariable Long id){
//        return propertyService.getCharacteristic(id);
//    }
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id){
//        return propertyService.delete(id);
//    }
}
