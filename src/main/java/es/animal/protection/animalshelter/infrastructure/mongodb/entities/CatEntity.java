package es.animal.protection.animalshelter.infrastructure.mongodb.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.animal.protection.animalshelter.domain.model.Cat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class CatEntity {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private Integer chip;
    private Boolean sociable;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String admissionDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String departureDate;
    @DBRef(lazy = true)
    private AdopterEntity adopterEntity;

    public CatEntity(Cat cat) {
        BeanUtils.copyProperties(cat, this);
    }

    public CatEntity(CatEntity catEntity, AdopterEntity adopterEntity) {
        BeanUtils.copyProperties(adopterEntity, this.adopterEntity);
        BeanUtils.copyProperties(catEntity, this);
    }

    public Cat toCat() {
        Cat cat = new Cat();
        BeanUtils.copyProperties(this, cat);
        if (this.adopterEntity != null) {
            cat.setAdopterNif(this.adopterEntity.getNif());
        }
        return cat;
    }
}
