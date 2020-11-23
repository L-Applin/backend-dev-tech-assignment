package ca.applin.octaveg.assignement.jukes.service.settings;

import ca.applin.octaveg.assignement.jukes.service.jukebox.Jukebox;
import ca.applin.octaveg.assignement.jukes.service.jukebox.JukeboxComponent;
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

    /**
     * The required Components for this setting to be supported. May be empty.
     *
     * @see Jukebox
     * @see JukeboxComponent
     */
    @JsonProperty
    private Set<String> requires;
}
