package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.Characteristic;

public interface CharacteristicRepository extends JpaRepository<Characteristic,Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
