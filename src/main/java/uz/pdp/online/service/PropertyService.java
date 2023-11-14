package uz.pdp.online.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.dto.PropertyDto;
import uz.pdp.online.entity.Characteristic;
import uz.pdp.online.entity.Property;
import uz.pdp.online.repository.CharacteristicRepository;
import uz.pdp.online.repository.ProductRepository;
import uz.pdp.online.repository.PropertyRepository;

import java.util.Optional;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final CharacteristicRepository characteristicRepository;
    public PropertyService(PropertyRepository propertyRepository,CharacteristicRepository characteristicRepository){
        this.propertyRepository=propertyRepository;
        this.characteristicRepository=characteristicRepository;
    }
    public ResponseEntity<?> addProperty(PropertyDto dto){
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(dto.getCharacteristicId());
        if(optionalCharacteristic.isEmpty()) return ResponseEntity.status(404).build();
        Property property=new Property();
        property.setName(dto.getValue());
        property.setCharacteristic(optionalCharacteristic.get());
        propertyRepository.save(property);
        return ResponseEntity.ok().build();
    }
}
