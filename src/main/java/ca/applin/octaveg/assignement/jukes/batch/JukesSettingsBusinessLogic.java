package ca.applin.octaveg.assignement.jukes.batch;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.service.jukebox.Jukebox;
import ca.applin.octaveg.assignement.jukes.service.jukebox.JukeboxComponent;
import ca.applin.octaveg.assignement.jukes.service.settings.JukeboxSetting;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class JukesSettingsBusinessLogic {

    public JukesEntity toJukesEntity(Jukebox juke, List<JukeboxSetting> jukeboxSettingList) {
        Set<String> components = juke.getComponents().stream()
                .map(JukeboxComponent::getName)
                .collect(toSet());
        List<String> supportedSettings = jukeboxSettingList.stream()
                .filter(jukeboxSetting -> components.containsAll(jukeboxSetting.getRequires()))
                .map(JukeboxSetting::getId)
                .collect(toList());
        return new JukesEntity(
                juke.getId(),
                juke.getModel(),
                supportedSettings,
                juke.getComponentsNames());
    }

}
