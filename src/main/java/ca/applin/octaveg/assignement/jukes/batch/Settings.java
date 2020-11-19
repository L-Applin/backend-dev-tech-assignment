package ca.applin.octaveg.assignement.jukes.batch;

import ca.applin.octaveg.assignement.jukes.service.settings.JukeboxSetting;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class Settings {

    @JsonProperty
    private List<JukeboxSetting> settings;
}
