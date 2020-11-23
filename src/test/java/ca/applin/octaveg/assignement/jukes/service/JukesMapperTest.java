package ca.applin.octaveg.assignement.jukes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.dto.JukesDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class JukesMapperTest {

    private JukesMapper jukesMapper = Mappers.getMapper(JukesMapper.class);

    @Test
    public void testMapper() {
        JukesDto dto = jukesMapper.jukesEntityToDto(JukesEntity.builder()
                .id("id1")
                .model("model1")
                .components(List.of("c1", "c2"))
                .settings(List.of("s1", "s2", "s3"))
                .build());

        assertEquals("id1", dto.getId());
        assertEquals("model1", dto.getModel());
        assertEquals(List.of("c1", "c2"), dto.getComponents());
        assertEquals(List.of("s1", "s2", "s3"), dto.getSettings());
    }

}