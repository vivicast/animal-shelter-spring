package es.animal.protection.animalshelter.infrastructure.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdopterDto {
    private List<String> adopterNifs;
}
