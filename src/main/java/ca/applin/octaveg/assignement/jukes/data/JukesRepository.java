package ca.applin.octaveg.assignement.jukes.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JukesRepository extends PagingAndSortingRepository<JukesEntity, String> {

    Page<JukesEntity> findBySettings(String setting, Pageable pageable);

    Page<JukesEntity> findBySettingsContainingAndModel(String setting, String model,
            Pageable pageable);
}
