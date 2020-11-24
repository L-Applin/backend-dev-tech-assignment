package ca.applin.octaveg.assignement.jukes.service.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JukeboxSetting {

    @JsonProperty
    private String id;

    @JsonProperty
    private Set<String> requires;
}
