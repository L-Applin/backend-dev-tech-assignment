package ca.applin.octaveg.assignement.jukes.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class JukesRepositoryTest {

    @Autowired
    private JukesRepository jukesRepository;

    @Test
    void given_jukes_with_setting_in_repo__When_findBySetting__Expect_jukes_with_that_setting() {
        // ARRANGE
        jukesRepository.saveAll(List.of(
                JukesEntity.builder()
                        .id("id_1")
                        .model("model_1")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s1", "s2", "s3"))
                        .build(),
                JukesEntity.builder()
                        .id("id_2")
                        .model("model_2")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s2", "s3"))
                        .build(),
                JukesEntity.builder()
                        .id("id_3")
                        .model("model_3")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s3"))
                        .build()
        ));

        // ACT
        List<JukesEntity> jukes_s1 = jukesRepository.findBySettings("s1", Pageable.unpaged())
                .getContent();
        List<JukesEntity> jukes_s2 = jukesRepository.findBySettings("s2", Pageable.unpaged())
                .getContent();
        List<JukesEntity> jukes_s3 = jukesRepository.findBySettings("s3", Pageable.unpaged())
                .getContent();

        // ASSERT
        assertEquals(1, jukes_s1.size());
        assertEquals(2, jukes_s2.size());
        assertEquals(3, jukes_s3.size());

        jukes_s1.forEach(juke -> assertTrue(juke.getSettings().contains("s1")));
        jukes_s2.forEach(juke -> assertTrue(juke.getSettings().contains("s2")));
        jukes_s3.forEach(juke -> assertTrue(juke.getSettings().contains("s3")));

        assertEquals(jukes_s1.get(0).getId(), "id_1");

        assertEquals(Set.of("id_1", "id_2"),
                jukes_s2.stream().map(JukesEntity::getId).collect(Collectors.toSet()));
        assertEquals(Set.of("id_1", "id_2", "id_3"),
                jukes_s3.stream().map(JukesEntity::getId).collect(Collectors.toSet()));


    }

    @Test
    void given_jukes_with_settings_in_repo__When_findBySetting_for_absent_settings__Expect_empty_resutl() {
        // ARRANGE
        jukesRepository.saveAll(List.of(
                JukesEntity.builder()
                        .id("id_1")
                        .model("model_1")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s1", "s2", "s3"))
                        .build()));
        // ACT
        Page<JukesEntity> emptyJukes = jukesRepository
                .findBySettings("setting-not-in-db", Pageable.unpaged());

        // ASSERT
        assertEquals(0, emptyJukes.getTotalElements());
    }

    @Test
    void given_jukes_with_settings_and_model__When_findBySettingsAndModel__Expect_jukes_with_those_settings_and_model() {
        // ARRANGE
        jukesRepository.saveAll(List.of(
                JukesEntity.builder()
                        .id("id_1")
                        .model("model_1")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s1", "s2", "s3"))
                        .build(),
                JukesEntity.builder()
                        .id("id_2")
                        .model("model_2")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s2", "s3"))
                        .build(),
                JukesEntity.builder()
                        .id("id_3")
                        .model("model_3")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s3"))
                        .build()
        ));

        // ACT
        List<JukesEntity> jukes_s1_model1 = jukesRepository
                .findBySettingsAndModel("s1", "model_1", Pageable.unpaged()).getContent();
        List<JukesEntity> jukes_s2_model1 = jukesRepository
                .findBySettingsAndModel("s2", "model_1", Pageable.unpaged()).getContent();
        List<JukesEntity> jukes_s3_model1 = jukesRepository
                .findBySettingsAndModel("s3", "model_1", Pageable.unpaged()).getContent();
        List<JukesEntity> jukes_s2_model2 = jukesRepository
                .findBySettingsAndModel("s2", "model_2", Pageable.unpaged()).getContent();
        List<JukesEntity> jukes_s3_model2 = jukesRepository
                .findBySettingsAndModel("s3", "model_2", Pageable.unpaged()).getContent();
        List<JukesEntity> jukes_s3_model3 = jukesRepository
                .findBySettingsAndModel("s3", "model_3", Pageable.unpaged()).getContent();

        // ASSERT
        assertEquals(1, jukes_s1_model1.size());
        assertEquals(1, jukes_s2_model1.size());
        assertEquals(1, jukes_s3_model1.size());
        assertEquals(1, jukes_s2_model2.size());
        assertEquals(1, jukes_s3_model2.size());
        assertEquals(1, jukes_s3_model3.size());
        assertEquals("id_1", jukes_s1_model1.get(0).getId());
        assertEquals("id_1", jukes_s2_model1.get(0).getId());
        assertEquals("id_1", jukes_s3_model1.get(0).getId());
        assertEquals("id_2", jukes_s2_model2.get(0).getId());
        assertEquals("id_2", jukes_s3_model2.get(0).getId());
        assertEquals("id_3", jukes_s3_model3.get(0).getId());
    }

    @Test
    void given_jukes_with_model_in_repo__When_findBySettingsAndModel_for_absent_model__Expect_empty_result() {
        // ARRANGE
        jukesRepository.saveAll(List.of(
                JukesEntity.builder()
                        .id("id_1")
                        .model("model_1")
                        .components(List.of("c1", "c2"))
                        .settings(List.of("s1", "s2", "s3"))
                        .build()));
        // ACT
        Page<JukesEntity> emptyJukes = jukesRepository
                .findBySettingsAndModel("s1", "model-absent-in-db", Pageable.unpaged());

        // ASSERT
        assertEquals(0, emptyJukes.getTotalElements());
    }
}