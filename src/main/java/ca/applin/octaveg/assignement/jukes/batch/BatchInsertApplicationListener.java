package ca.applin.octaveg.assignement.jukes.batch;

import static java.util.stream.Collectors.toList;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.data.JukesRepository;
import ca.applin.octaveg.assignement.jukes.service.jukebox.JukeboxService;
import ca.applin.octaveg.assignement.jukes.service.settings.JukeboxSetting;
import ca.applin.octaveg.assignement.jukes.service.settings.SettingsService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class BatchInsertApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final JukeboxService jukeboxService;
    private final SettingsService settingsService;
    private final JukesRepository jukesRepository;
    private final JukesSettingsBusinessLogic jukesBusinessLogic;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<JukeboxSetting> jukeboxSettingList = settingsService.getAllSettings();
        List<JukesEntity> jukesToSave = jukeboxService.getAllJukeboxes().stream()
                .map(juke -> jukesBusinessLogic.toJukesEntity(juke, jukeboxSettingList))
                .collect(toList());
        jukesRepository.saveAll(jukesToSave);
        log.info("Saved {} mock jukebox entities into Database.", jukesRepository.count());
    }

}
