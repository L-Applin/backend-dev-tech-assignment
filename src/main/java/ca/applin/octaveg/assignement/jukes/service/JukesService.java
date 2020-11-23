package ca.applin.octaveg.assignement.jukes.service;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.data.JukesRepository;
import ca.applin.octaveg.assignement.jukes.dto.JukesDto;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JukesService {

    private final JukesRepository repository;
    private final JukesMapper jukesMapper;

    public Page<JukesDto> getAllJukesBySettingId(String settingId, Optional<String> optModel,
            Optional<Integer> offset, Optional<Integer> limit) {
        Pageable pageable = pageRequestFromOffsetLimit(offset, limit);
        return optModel
                .map(model -> repository.findBySettingsAndModel(settingId, model, pageable))
                .orElseGet(() -> repository.findBySettings(settingId, pageable))
                .map(jukesMapper::jukesEntityToDto);
    }

    private Pageable pageRequestFromOffsetLimit(Optional<Integer> offset, Optional<Integer> limit) {
        int pageLimit = limit.orElse(Integer.MAX_VALUE);
        int pageOffset = offset.orElse(0);
        int pageNumer = (pageOffset / pageLimit) + (pageOffset % pageLimit);
        return PageRequest.of(pageNumer, pageLimit, Sort.by(JukesEntity.ID_PROPERTY));
    }
}
