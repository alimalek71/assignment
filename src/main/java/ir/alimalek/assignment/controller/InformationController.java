package ir.alimalek.assignment.controller;

import ir.alimalek.assignment.dto.InformationDTO;
import ir.alimalek.assignment.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/information")
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;


    @GetMapping
    public ResponseEntity<Page<InformationDTO>> getByPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(informationService.findAll(pageable));
    }

    @GetMapping("/{code}")
    public ResponseEntity<InformationDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok().body(informationService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam("file") MultipartFile file) {
        informationService.uploadCSV(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        informationService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
