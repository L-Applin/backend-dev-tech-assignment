package ca.applin.octaveg.assignement.jukes.batch;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.service.jukebox.Jukebox;
import ca.applin.octaveg.assignement.jukes.service.jukebox.JukeboxComponent;
import ca.applin.octaveg.assignement.jukes.service.settings.JukeboxSetting;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JukesSettingsBusinessLogicTest {

    private JukesSettingsBusinessLogic settingsLogic;

    @BeforeEach
    public void init() {
        settingsLogic = new JukesSettingsBusinessLogic();
    }


    @Test
    public void given_setting_without_requirements__When_jukebox_has_any_component__Expect_juke_entity_with_setting() {
        // ARRANGE
        JukeboxSetting noRequiremetnSetting = new JukeboxSetting("settings-id", emptySet());

        JukeboxComponent component1 = JukeboxComponent.builder().name("c1").build();
        JukeboxComponent component2 = JukeboxComponent.builder().name("c2").build();

        Jukebox jukeboxNoComponent = Jukebox.builder()
                .id("id1")
                .model("model1")
                .components(emptyList())
                .build();

        Jukebox jukeboxSingleComponent = Jukebox.builder()
                .id("id2")
                .model("model2")
                .components(List.of(component1))
                .build();

        Jukebox jukeboxMultipleComponent = Jukebox.builder()
                .id("id3")
                .model("model3")
                .components(List.of(component1, component2))
                .build();

        // ACT
        JukesEntity entityNoComponent = settingsLogic
                .toJukesEntity(jukeboxNoComponent, singletonList(noRequiremetnSetting));
        JukesEntity entitySingleComponent = settingsLogic
                .toJukesEntity(jukeboxSingleComponent, singletonList(noRequiremetnSetting));
        JukesEntity entityMultipleComponent = settingsLogic
                .toJukesEntity(jukeboxMultipleComponent, singletonList(noRequiremetnSetting));

        // ASSERT
        assertEquals(1, entityNoComponent.getSettings().size());
        assertEquals(1, entitySingleComponent.getSettings().size());
        assertEquals(1, entityMultipleComponent.getSettings().size());

        assertEquals("settings-id", entityNoComponent.getSettings().get(0));
        assertEquals("settings-id", entitySingleComponent.getSettings().get(0));
        assertEquals("settings-id", entityMultipleComponent.getSettings().get(0));
    }


    @Test
    public void given_setting_with_single_requirement__When_jukebox_has_that_component__Expect_entity_with_that_setting() {
        // ARRANGE
        JukeboxSetting singleRequirementSetting = new JukeboxSetting("setting-id",
                Set.of("comp-1"));

        Jukebox jukeboxWithComponentRequired = Jukebox.builder()
                .id("id1")
                .model("model1")
                .components(List.of(JukeboxComponent.builder().name("comp-1").build()))
                .build();

        // ACT
        JukesEntity entityWithComponent = settingsLogic.toJukesEntity(jukeboxWithComponentRequired,
                singletonList(singleRequirementSetting));

        // ASSERT
        assertEquals(1, entityWithComponent.getSettings().size());
        assertEquals("setting-id", entityWithComponent.getSettings().get(0));
    }


    @Test
    public void given_setting_with_single_requirement__When_jukebox_doesnt_have_that_component__Expect_entity_without_setting() {
        // ARRANGE
        JukeboxSetting singleRequirementSetting = new JukeboxSetting("setting-id",
                Set.of("comp-1"));

        JukeboxComponent component1 = JukeboxComponent.builder().name("wrong1")
                .build(); // wrong component
        JukeboxComponent component2 = JukeboxComponent.builder().name("wrong2")
                .build(); // wrong componentk

        Jukebox jukeboxNoComponent = Jukebox.builder()
                .id("id1")
                .model("model1")
                .components(emptyList())
                .build();

        Jukebox jukeboxSingleComponent = Jukebox.builder()
                .id("id2")
                .model("model2")
                .components(List.of(component1))
                .build();

        Jukebox jukeboxMultipleComponent = Jukebox.builder()
                .id("id3")
                .model("model3")
                .components(List.of(component1, component2))
                .build();

        // ACT
        JukesEntity entityNoSetting1 = settingsLogic
                .toJukesEntity(jukeboxNoComponent, singletonList(singleRequirementSetting));
        JukesEntity entityNoSetting2 = settingsLogic
                .toJukesEntity(jukeboxSingleComponent, singletonList(singleRequirementSetting));
        JukesEntity entityNoSetting3 = settingsLogic
                .toJukesEntity(jukeboxMultipleComponent, singletonList(singleRequirementSetting));

        // ASSERT
        assertTrue(entityNoSetting1.getSettings().isEmpty());
        assertTrue(entityNoSetting2.getSettings().isEmpty());
        assertTrue(entityNoSetting3.getSettings().isEmpty());
    }

    @Test
    public void given_setting_with_multiple_requirements__When_jukeboxes_has_all_components__Expect_entity_with_setting() {
        // ARRANGE
        JukeboxSetting settingsRequirement = new JukeboxSetting("setting-id",
                Set.of("comp-1", "comp-2"));

        JukeboxComponent component1 = JukeboxComponent.builder().name("comp-1").build();
        JukeboxComponent component2 = JukeboxComponent.builder().name("comp-2").build();
        JukeboxComponent component3 = JukeboxComponent.builder().name("comp-3").build();

        Jukebox jukeboxCorrectComponentOnly = Jukebox.builder()
                .id("id2")
                .model("model2")
                .components(List.of(component1, component2))
                .build();

        Jukebox jukeboxCorrectComponentAndMore = Jukebox.builder()
                .id("id3")
                .model("model3")
                .components(List.of(component1, component2, component3))
                .build();

        // ACT
        JukesEntity entityCorrectOnly = settingsLogic
                .toJukesEntity(jukeboxCorrectComponentOnly, singletonList(settingsRequirement));
        JukesEntity entityCorrectAndMore = settingsLogic
                .toJukesEntity(jukeboxCorrectComponentAndMore, singletonList(settingsRequirement));

        // ASSERT
        assertEquals(1, entityCorrectOnly.getSettings().size());
        assertEquals(1, entityCorrectAndMore.getSettings().size());

        assertTrue(entityCorrectOnly.getSettings().contains("setting-id"));
        assertTrue(entityCorrectAndMore.getSettings().contains("setting-id"));
    }

    @Test
    public void given_multiple_settings__When_jukebox_staisfy_all_settings__Expect_entities_with_all_settiongs() {
        // ARRANGE
        JukeboxSetting multipleRequirementSetting = new JukeboxSetting("setting-id-1-2",
                Set.of("comp-1", "comp-2"));
        JukeboxSetting singleRequirementSetting = new JukeboxSetting("setting-id-3",
                Set.of("comp-3"));
        List<JukeboxSetting> allSettings = List
                .of(singleRequirementSetting, multipleRequirementSetting);

        JukeboxComponent component1 = JukeboxComponent.builder().name("comp-1").build();
        JukeboxComponent component2 = JukeboxComponent.builder().name("comp-2").build();
        JukeboxComponent component3 = JukeboxComponent.builder().name("comp-3").build();

        Jukebox jukeboxSingleComponent = Jukebox.builder()
                .id("id1")
                .model("model1")
                .components(singletonList(component3))
                .build();

        Jukebox jukeboxMultipleComponents = Jukebox.builder()
                .id("id1")
                .model("model2")
                .components(List.of(component1, component2))
                .build();

        Jukebox jukeboxAllComponents = Jukebox.builder()
                .id("id3")
                .model("model3")
                .components(List.of(component2, component3, component1)) // order shoul dnot matter
                .build();

        // ACT
        JukesEntity entitySingleComponent = settingsLogic
                .toJukesEntity(jukeboxSingleComponent, allSettings);
        JukesEntity entityMultipleComponent = settingsLogic
                .toJukesEntity(jukeboxMultipleComponents, allSettings);
        JukesEntity entityAllComponent = settingsLogic
                .toJukesEntity(jukeboxAllComponents, allSettings);

        // ASSERT
        assertEquals(1, entitySingleComponent.getSettings().size());
        assertEquals(1, entityMultipleComponent.getSettings().size());
        assertEquals(2, entityAllComponent.getSettings().size());

        assertEquals("setting-id-3", entitySingleComponent.getSettings().get(0));
        assertEquals("setting-id-1-2", entityMultipleComponent.getSettings().get(0));
        assertTrue(entityAllComponent.getSettings()
                .containsAll(List.of("setting-id-1-2", "setting-id-3")));
    }
}