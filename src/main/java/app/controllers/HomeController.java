package app.controllers;

import app.entities.Doctor;
import app.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    private final DoctorRepository doctorRepository;

    @Autowired
    public HomeController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/")
    public List<Doctor> index() {
        return doctorRepository.findAll();
    }
}
