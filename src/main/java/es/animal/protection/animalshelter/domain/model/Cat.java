package es.animal.protection.animalshelter.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat {

    private String name;
    @NotNull
    private Integer chip;
    private Boolean sociable;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String admissionDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String departureDate;
    private String adopterNif;
    private String colonyRegistry;

}
