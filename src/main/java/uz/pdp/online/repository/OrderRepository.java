package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
