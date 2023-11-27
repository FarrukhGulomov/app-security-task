package uz.pdp.online.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.online.entity.utils.General;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
public class Property extends General {
    @ManyToOne
    private Characteristic characteristic;
}
