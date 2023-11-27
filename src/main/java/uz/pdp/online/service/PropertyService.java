package uz.pdp.online.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.dto.PropertyDto;
import uz.pdp.online.entity.Characteristic;
import uz.pdp.online.entity.Property;
import uz.pdp.online.repository.CharacteristicRepository;
import uz.pdp.online.repository.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final CharacteristicRepository characteristicRepository;

    public PropertyService(PropertyRepository propertyRepository, CharacteristicRepository characteristicRepository) {
        this.propertyRepository = propertyRepository;
        this.characteristicRepository = characteristicRepository;
    }



    public ResponseEntity<?> add(PropertyDto dto) {
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(dto.getCharacteristicId());
        if(optionalCharacteristic.isEmpty()) return ResponseEntity.status(404).build();

        Property property=new Property();
        property.setCharacteristic(optionalCharacteristic.get());
        property.setName(dto.getProperty());
        propertyRepository.save(property);
        return ResponseEntity.ok().build();
    }




}
