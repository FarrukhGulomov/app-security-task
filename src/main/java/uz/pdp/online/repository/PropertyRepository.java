package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.Property;

public interface PropertyRepository extends JpaRepository<Property,Long> {
}
