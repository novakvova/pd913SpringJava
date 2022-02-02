package app.controllers;

import app.entities.Badge;
import app.repositories.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badges/")
public class BadgeController {
    private final BadgeRepository regionRepository;

    @GetMapping("/all")
    public Page<Badge> getById() {
        Sort sort = Sort.by("Id");
        long time = System.nanoTime();
        Pageable pageable = PageRequest.of(0,1000000,sort);
        Page<Badge> list = regionRepository.findAll(pageable);
        long result = System.nanoTime() - time;
        double second=result/1000000000.0;
        System.out.println((System.nanoTime() - time) + "ns per million");
        System.out.println(second+ "seconds");
        pageable = PageRequest.of(0,10,sort);
        return regionRepository.findAll(pageable);
    }
}
