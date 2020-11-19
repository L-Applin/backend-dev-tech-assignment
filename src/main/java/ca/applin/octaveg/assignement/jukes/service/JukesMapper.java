package ca.applin.octaveg.assignement.jukes.service;

import ca.applin.octaveg.assignement.jukes.data.JukesEntity;
import ca.applin.octaveg.assignement.jukes.dto.JukesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JukesMapper {

    JukesDto jukesEntityuToDto(JukesEntity jukesEntity);
}
