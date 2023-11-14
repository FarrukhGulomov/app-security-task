package uz.pdp.online.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDto {

    private String name;

    private Long parentCategoryId;
}
