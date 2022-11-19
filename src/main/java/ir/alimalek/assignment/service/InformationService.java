package ir.alimalek.assignment.service;

import ir.alimalek.assignment.dto.InformationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface InformationService {
    Page<InformationDTO> findAll(Pageable pageable);

    InformationDTO findByCode(String code);

    void uploadCSV(MultipartFile file);

    void deleteAll();
}
