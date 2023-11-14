package uz.pdp.online.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.entity.Characteristic;
import uz.pdp.online.repository.CharacteristicRepository;

import java.util.Optional;

@Service
public class CharacteristicService {
    private final CharacteristicRepository characteristicRepository;

    public CharacteristicService(CharacteristicRepository characteristicRepository) {
        this.characteristicRepository = characteristicRepository;
    }

    public ResponseEntity<?> addCharacteristic(Characteristic characteristic) {
        boolean existsByName = characteristicRepository.existsByName(characteristic.getName());
        if (existsByName) return ResponseEntity.status(405).body("Already exist!");
        characteristicRepository.save(characteristic);
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<?> getCharacteristics(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return ResponseEntity.ok(characteristicRepository.findAll(pageable));
    }

    public ResponseEntity<?> getCharacteristic(Long id) {
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(id);
        if (optionalCharacteristic.isEmpty()) return ResponseEntity.status(404).build();
        return ResponseEntity.ok(optionalCharacteristic.get());
    }

    public ResponseEntity<?> editCharacteristic(Long id, Characteristic characteristic) {
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(id);
        if (optionalCharacteristic.isEmpty()) return ResponseEntity.status(404).build();
        boolean existsByNameAndIdNot = characteristicRepository.existsByNameAndIdNot(characteristic.getName(), id);
        if (existsByNameAndIdNot) return ResponseEntity.status(405).build();

        Characteristic editingCharacteristic = optionalCharacteristic.get();
        editingCharacteristic.setName(characteristic.getName());
        characteristicRepository.save(editingCharacteristic);
        return ResponseEntity.status(200).build();

    }

    public ResponseEntity<?> deleteCharacteristic(Long id) {
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(id);
        if (optionalCharacteristic.isEmpty()) return ResponseEntity.status(404).build();

        characteristicRepository.deleteById(id);
        return ResponseEntity.status(200).build();


    }
}
