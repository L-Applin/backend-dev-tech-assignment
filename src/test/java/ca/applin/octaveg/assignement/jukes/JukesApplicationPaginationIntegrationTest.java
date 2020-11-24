package ca.applin.octaveg.assignement.jukes;

import static ca.applin.octaveg.assignement.jukes.JukesConstant.BASE_JUKES_URL;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.data.JukesRepository;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource(properties = "mock.db.location=classpath:/mocks/db-test.json")
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JukesApplicationPaginationIntegrationTest {

    @Autowired
    private JukesRepository jukesRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        jukesRepository.deleteAll();
        List<JukesEntity> jukesToSave = Stream.generate(new JukesGenerator(0))
                .limit(1000)
                .collect(Collectors.toList());
        jukesRepository.saveAll(jukesToSave);
    }

    private static class JukesGenerator implements Supplier<JukesEntity> {

        private int iterator;

        public JukesGenerator(int start) {
            this.iterator = start;
        }

        @Override
        public JukesEntity get() {
            return JukesEntity.builder()
                    .id("id" + iterator++)
                    .model("model-test")
                    .settings(List.of("setting-test"))
                    .components(List.of("conponent-test"))
                    .build();
        }
    }

    @Test
    public void when_find_for_page__Expect_that_number_only() throws Exception {
        mockMvc.perform(get(BASE_JUKES_URL)
                .param("settingId", "setting-test")
                .param("offset", "10")
                .param("limit", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)));
    }

    @Test
    public void when_no_paging__Expect_everything() throws Exception {
        mockMvc.perform(get(BASE_JUKES_URL)
                .param("settingId", "setting-test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1000)));
    }

}
