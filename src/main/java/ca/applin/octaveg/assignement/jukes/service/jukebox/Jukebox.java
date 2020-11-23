package ca.applin.octaveg.assignement.jukes.service.jukebox;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jukebox {

    @JsonProperty
    private String id;
    @JsonProperty
    private String model;
    @JsonProperty
    private List<JukeboxComponent> components;

    @JsonIgnore
    public List<String> getComponentsNames() {
        return components.stream().map(JukeboxComponent::getName).collect(Collectors.toList());
    }
}
