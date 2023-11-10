package uz.pdp.online.entity;

import jakarta.annotation.security.DenyAll;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.online.entity.utils.Currency;
import uz.pdp.online.entity.utils.Measurement;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    @ManyToOne
    private Product product;
    private Measurement measurement = Measurement.QUANTITY;
    private Currency currency = Currency.UZS;
    private Long amount;
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

}
