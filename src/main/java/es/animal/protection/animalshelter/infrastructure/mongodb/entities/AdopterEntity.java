package es.animal.protection.animalshelter.infrastructure.mongodb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
@Builder
public class AdopterEntity {
    private String name;
    @Id
    private String nif;
    private String address;
    private String birthDay;
}
