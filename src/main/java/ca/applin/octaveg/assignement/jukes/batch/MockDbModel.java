package ca.applin.octaveg.assignement.jukes.batch;

import ca.applin.octaveg.assignement.jukes.service.jukebox.Jukebox;
import ca.applin.octaveg.assignement.jukes.service.settings.JukeboxSetting;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class MockDbModel {

    @JsonProperty
    private Settings settings;
    @JsonProperty("jukes")
    private List<Jukebox> jukeboxes;

    @JsonIgnore
    public List<JukeboxSetting> getJukeboxSettings() {
        return settings.getSettings();
    }
}
