package ca.applin.octaveg.assignement.jukes.batch;

import ca.applin.octaveg.assignement.jukes.service.jukebox.Jukebox;
import ca.applin.octaveg.assignement.jukes.service.settings.JukeboxSetting;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * Class for reading the mock bd json file
 */
@Service
@RequiredArgsConstructor
public class FileReader implements InitializingBean {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private MockDbModel mockDbModel;

    @Value("${mock.db.location}")
    private String mockDbJsonFilePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource jsonFile = resourceLoader.getResource(mockDbJsonFilePath);
        this.mockDbModel = objectMapper.readerFor(MockDbModel.class)
                .readValue(jsonFile.getInputStream());
    }


    public List<JukeboxSetting> jukeboxSettings() {
        return mockDbModel.getJukeboxSettings();
    }

    public List<Jukebox> jukeboxes() {
        return mockDbModel.getJukeboxes();
    }

}
