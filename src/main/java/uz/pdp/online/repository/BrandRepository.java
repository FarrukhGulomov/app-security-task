package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.entity.Brand;
@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

}
