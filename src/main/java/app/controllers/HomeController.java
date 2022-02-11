package app.controllers;

import app.dto.CreateDoctorDto;
import app.entities.Doctor;
import app.mapper.DoctorMapper;
import app.repositories.DoctorRepository;
import app.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final StorageService storageService;

    @GetMapping("/")
    public List<Doctor> index() {
        return doctorRepository.findAll();
    }

    @PostMapping("/create")
    public Doctor index(CreateDoctorDto dto) throws Exception {

        String [] charArray = dto.getPhoto().split(",");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = new byte[0];
        bytes = decoder.decode(charArray[1]);
        String directory= "uploaded/"+"app.jpg"; //servletContext.getRealPath("/")+"images/sample.jpg";

        new FileOutputStream(directory).write(bytes);
        Doctor doctor = doctorMapper.CreateDoctorToDoctor(dto);
        doctorRepository.save(doctor);
        return doctor;
    }
}
