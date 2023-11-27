package uz.pdp.online.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.online.entity.utils.General;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends General {

    private BigDecimal price = BigDecimal.ZERO;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Category category;

    private String description;

    @ManyToMany
    private List<Property> Properties;

    @OneToMany
    private List<Attachment> productPhotos;

    private Double amount;
}
