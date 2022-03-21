package app.controllers;

import app.dto.CreateDoctorDto;
import app.dto.UploadImageDto;
import app.entities.Doctor;
import app.mapper.DoctorMapper;
import app.repositories.DoctorRepository;
import app.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    public Doctor create(@RequestBody CreateDoctorDto dto) throws Exception {
        String fileName = storageService.store(dto.getPhoto());
        Doctor doctor = doctorMapper.CreateDoctorToDoctor(dto);
        doctorRepository.save(doctor);
        return doctor;
    }
    @PostMapping("/upload")
    public String upload(@RequestBody UploadImageDto dto) {
        String fileName = storageService.store(dto.getBase64());
        return fileName;
    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {

        Resource file = storageService.loadAsResource(filename);
        String urlFileName =  URLEncoder.encode("сало.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                .contentType(MediaType.IMAGE_JPEG)

                .header(HttpHeaders.CONTENT_DISPOSITION,"filename=\""+urlFileName+"\"")
                .body(file);
    }

}
