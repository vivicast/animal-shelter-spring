package es.animal.protection.animalshelter.infrastructure.mongodb.entities;

import es.animal.protection.animalshelter.domain.model.Colony;
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
public class ColonyEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String registry;
    private String manager;
    private String phone;
    private String location;

    public ColonyEntity(Colony colony) {
        BeanUtils.copyProperties(colony, this);
    }

    public Colony toColony() {
        Colony colony = new Colony();
        BeanUtils.copyProperties(this, colony);
        return colony;
    }
}
