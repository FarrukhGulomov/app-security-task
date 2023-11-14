package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Characteristic;
import uz.pdp.online.service.CharacteristicService;

@RestController
@RequestMapping("/api/characteristics")
public class CharacteristicController {

    public final CharacteristicService characteristicService;
    public CharacteristicController(CharacteristicService characteristicService){
        this.characteristicService=characteristicService;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCharacteristic(@PathVariable Long id){
        return characteristicService.getCharacteristic(id);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<?> getCharacteristics(@RequestParam int pageNumber,@RequestParam int pageSize){
        return characteristicService.getCharacteristics(pageNumber,pageSize);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<?> addCharacteristic(@RequestBody Characteristic characteristic){
        return characteristicService.addCharacteristic(characteristic);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody Characteristic characteristic){
        return characteristicService.editCharacteristic(id,characteristic);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return characteristicService.deleteCharacteristic(id);
    }
}
