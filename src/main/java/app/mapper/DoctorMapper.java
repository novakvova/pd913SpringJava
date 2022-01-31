package app.mapper;

import app.dto.CreateDoctorDto;
import app.entities.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor CreateDoctorToDoctor(CreateDoctorDto doctorDto);
}
