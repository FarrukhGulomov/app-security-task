package uz.pdp.online.dto;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.online.entity.Attachment;
import uz.pdp.online.entity.Brand;
import uz.pdp.online.entity.Category;
import uz.pdp.online.entity.Characteristic;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDto {

    private String name;

    private Boolean active;

    private BigDecimal price;

    private Long brandId;

    private Long categoryId;

    private String description;

    private List<Long> characteristicsIds;

    private List<Long> productPhotosIds;

}
