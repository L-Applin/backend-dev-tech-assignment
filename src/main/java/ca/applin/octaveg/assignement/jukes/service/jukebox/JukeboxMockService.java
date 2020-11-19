package ca.applin.octaveg.assignement.jukes.service.jukebox;

import ca.applin.octaveg.assignement.jukes.batch.FileReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JukeboxMockService implements JukeboxService {

    private final FileReader fileReader;

    @Override
    public List<Jukebox> getAllJukeboxes() {
        return fileReader.jukeboxes();
    }
}
