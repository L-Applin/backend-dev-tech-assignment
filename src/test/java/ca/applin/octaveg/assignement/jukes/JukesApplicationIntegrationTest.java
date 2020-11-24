package ca.applin.octaveg.assignement.jukes;

import static ca.applin.octaveg.assignement.jukes.JukesConstant.BASE_JUKES_URL;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource(properties = "mock.db.location=classpath:/mocks/db-test.json")
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class JukesApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_settingId_exist__Expect_200() throws Exception {
        // in test json, only jukebox id-1 has settiongs-id-1
        mockMvc.perform(get(BASE_JUKES_URL)
                .param("settingId", "setting-id-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("id-1")))
                .andExpect(jsonPath("$[0].model", is("model-1")))
                .andExpect(jsonPath("$[0].settings", hasSize(4)))
                .andExpect(jsonPath("$[0].settings",
                        containsInAnyOrder("setting-id-1", "setting-id-2", "setting-id-3",
                                "setting-id-empty")))
                .andExpect(jsonPath("$[0].components", containsInAnyOrder("r1", "r2", "r3")));
    }

    @Test
    public void get_settingId_empty_setting__Expect_200_all_jukes() throws Exception {
        mockMvc.perform(get(BASE_JUKES_URL)
                .param("settingId", "setting-id-empty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void get_setting_id_not_existing__Expect_204_no_content() throws Exception {
        mockMvc.perform(get(BASE_JUKES_URL)
                .param("settingId", "something-that-does-not-exist"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void get_without_settingId__Expect_400_bad_request() throws Exception {
        mockMvc.perform(get(BASE_JUKES_URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].status", is(400)))
                .andExpect(jsonPath("$.errors[0].message", notNullValue()))
                .andExpect(jsonPath("$.errors[0].details", notNullValue()))
                .andExpect(jsonPath("$.errors[0].path", notNullValue()));
    }

    @Test
    public void get_settingId_and_model_present__Expect_200_OK() throws Exception {
        mockMvc.perform(get(BASE_JUKES_URL)
                .param("settingId", "setting-id-2")
                .param("model", "model-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].model", is("model-1")));
    }

}