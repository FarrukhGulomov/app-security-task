package uz.pdp.online.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import uz.pdp.online.entity.utils.General;

@EqualsAndHashCode(callSuper = true)
@Entity
public class Category extends General {

    @ManyToOne
    private Category parentCategory;
}
