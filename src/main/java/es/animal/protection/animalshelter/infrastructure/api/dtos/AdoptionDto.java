package es.animal.protection.animalshelter.infrastructure.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionDto {
    @NotNull
    private Integer chip;
    @NotNull
    private String nifAdopter;
}
