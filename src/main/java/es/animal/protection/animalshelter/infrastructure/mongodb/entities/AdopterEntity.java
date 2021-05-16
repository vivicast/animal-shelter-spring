package es.animal.protection.animalshelter.infrastructure.mongodb.entities;

import es.animal.protection.animalshelter.domain.model.Adopter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class AdopterEntity {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String nif;
    private String address;
    private String birthDay;

    public AdopterEntity(Adopter adopter) {
        BeanUtils.copyProperties(adopter, this);
    }

    public Adopter toAdopter() {
        Adopter adopter = new Adopter();
        BeanUtils.copyProperties(this, adopter);
        return adopter;
    }
}
