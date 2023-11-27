package uz.pdp.online.entity;

import jakarta.annotation.security.DenyAll;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.online.entity.utils.Currency;
import uz.pdp.online.entity.utils.Measurement;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Product> products;
    private Measurement measurement = Measurement.QUANTITY;
    private Currency currency = Currency.UZS;
    private BigDecimal total;
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

}
