package ir.alimalek.assignment.mapper;

import ir.alimalek.assignment.domain.Information;
import ir.alimalek.assignment.dto.InformationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InformationMapper {
    InformationDTO toDTO(Information entity);

    List<InformationDTO> toDTO(List<Information> entity);

    Information toEntity(InformationDTO dto);
}
