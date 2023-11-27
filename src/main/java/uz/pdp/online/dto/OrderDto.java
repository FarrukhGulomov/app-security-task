package uz.pdp.online.dto;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.online.entity.Product;
import uz.pdp.online.entity.User;
import uz.pdp.online.entity.utils.Currency;
import uz.pdp.online.entity.utils.Measurement;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDto {

    private Long userId;
    private List<Long> productsIds;

    
}
