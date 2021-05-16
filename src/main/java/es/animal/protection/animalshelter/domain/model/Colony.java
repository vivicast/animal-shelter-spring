package es.animal.protection.animalshelter.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colony {
    @NotNull
    private String registry;
    @NotNull
    private String manager;
    private String phone;
    private String location;
}
