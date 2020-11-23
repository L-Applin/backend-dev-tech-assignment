package ca.applin.octaveg.assignement.jukes.service;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.dto.JukesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JukesMapper {

    // todo @temp
    // check why mapstructs returns always null, or why errors is thrown when
    // manually specify source and target properties
    default JukesDto jukesEntityToDto(JukesEntity jukesEntity) {
        if (jukesEntity == null) {
            return null;
        }
        return JukesDto.builder()
                .id(jukesEntity.getId())
                .model(jukesEntity.getModel())
                .settings(jukesEntity.getSettings())
                .components(jukesEntity.getComponents())
                .build();
    }
}
