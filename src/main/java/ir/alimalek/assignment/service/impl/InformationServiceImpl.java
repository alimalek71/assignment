package ir.alimalek.assignment.service.impl;

import ir.alimalek.assignment.component.InformationCSVComponent;
import ir.alimalek.assignment.domain.Information;
import ir.alimalek.assignment.dto.InformationDTO;
import ir.alimalek.assignment.exception.InformationNotFound;
import ir.alimalek.assignment.mapper.InformationMapper;
import ir.alimalek.assignment.repository.InformationRepository;
import ir.alimalek.assignment.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {

    private final InformationCSVComponent informationCSVComponent;
    private final InformationRepository informationRepository;
    private final InformationMapper informationMapper;

    @Override
    public Page<InformationDTO> findAll(Pageable pageable) {
        Page<Information> page = informationRepository.findAll(pageable);
        return new PageImpl<>(informationMapper.toDTO(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public InformationDTO findByCode(String code) {
        Information information = informationRepository.findByCode(code).orElseThrow(InformationNotFound::new);
        return informationMapper.toDTO(information);
    }

    @Override
    @Transactional
    public void uploadCSV(MultipartFile file) {
        List<Information> informationList = informationCSVComponent.csvToInformation(file);
        informationRepository.saveAll(informationList);
    }

    @Override
    public void deleteAll() {
        informationRepository.deleteAll();
    }
}
