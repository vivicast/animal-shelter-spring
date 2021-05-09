package es.animal.protection.animalshelter.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adopter {
    private String name;
    private String nif;
    private String address;
    private String birthDay;
}
