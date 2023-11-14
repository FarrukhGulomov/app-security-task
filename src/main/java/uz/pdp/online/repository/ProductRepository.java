package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
