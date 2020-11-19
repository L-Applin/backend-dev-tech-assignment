package ca.applin.octaveg.assignement.jukes.service.settings;

import ca.applin.octaveg.assignement.jukes.batch.FileReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsMockService implements SettingsService {

    private final FileReader fileReader;

    @Override
    public List<JukeboxSetting> getAllSettings() {
        return fileReader.jukeboxSettings();
    }
}
