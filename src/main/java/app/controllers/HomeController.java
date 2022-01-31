package app.controllers;

import app.dto.CreateDoctorDto;
import app.entities.Doctor;
import app.mapper.DoctorMapper;
import app.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @GetMapping("/")
    public List<Doctor> index() {
        return doctorRepository.findAll();
    }

    @PostMapping("/create")
    public Doctor index(CreateDoctorDto dto) {
        Doctor doctor = doctorMapper.CreateDoctorToDoctor(dto);
        doctorRepository.save(doctor);
        return doctor;
    }
}
