package ca.applin.octaveg.assignement.jukes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
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
