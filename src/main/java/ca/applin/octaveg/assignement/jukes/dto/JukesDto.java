package ca.applin.octaveg.assignement.jukes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JukesDto {

    @JsonProperty
    private String id;
    @JsonProperty
    private String model;
    @JsonProperty
    private List<String> settings;
    @JsonProperty
    private List<String> components;
}
