package dasturlash.uz.service;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public SectionDTO create(SectionDTO dto){

       Optional<SectionEntity> optional = sectionRepository.findBySectionKey(dto.getSectionKey());

       if(optional.isPresent()){
           throw new AppBadException("Section already exist");
       }

       SectionEntity entity = new SectionEntity();

       entity.setOrderNumber(dto.getOrderNumber());
       entity.setNameUz(dto.getNameUz());
       entity.setNameEn(dto.getNameEn());
       entity.setNameRu(dto.getNameRu());
       entity.setSectionKey(dto.getSectionKey());

       sectionRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public SectionDTO update(Integer id, SectionDTO dto){

        Optional<SectionEntity> optional = sectionRepository.findByIdAndVisibleTrue(id);

        if(optional.isEmpty()){
            throw new AppBadException("Section not found");
        }

        Optional<SectionEntity> keyOptional = sectionRepository.findBySectionKey(dto.getSectionKey());

        if(keyOptional.isPresent() && !id.equals(keyOptional.get().getId())){
            throw new AppBadException("Section key already exist");
        }

        SectionEntity entity = optional.get();
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setSectionKey(dto.getSectionKey());

        sectionRepository.save(entity);

        return dto;
    }

    public Boolean delete(Integer id){
       return sectionRepository.updateVisibleById(id, false) == 1;
    }

    public PageImpl<SectionDTO> pagination(int page, int size){

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("orderNumber").descending());

        Page<SectionEntity> pageEntity = sectionRepository.findAllByVisibleIsTrue(pageRequest);

        long total = pageEntity.getTotalElements();

        List<SectionEntity> entityList = pageEntity.getContent();
        List<SectionDTO> dtoList = new ArrayList<>();

        for (SectionEntity entity : entityList) {

            SectionDTO dto = new SectionDTO();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setNameUz(entity.getNameUz());
            dto.setNameEn(entity.getNameEn());
            dto.setNameRu(entity.getNameRu());
            dto.setSectionKey(entity.getSectionKey());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);

        }

        return new PageImpl<>(dtoList, pageRequest, total);
    }

    public List<SectionDTO> getAllByLang(AppLanguageEnum lang){

        Iterable<SectionEntity> entityList = sectionRepository.findAllByVisibleIsTrue();
        List<SectionDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(toLanguageResponseDTO(entity,lang)));

        return dtoList;
    }

    private SectionDTO toLanguageResponseDTO(SectionEntity entity, AppLanguageEnum lang) {

        SectionDTO dto = new SectionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setSectionKey(entity.getSectionKey());
        switch (lang){
            case UZ -> dto.setNameUz(entity.getNameUz());
            case RU -> dto.setNameRu(entity.getNameRu());
            case EN -> dto.setNameEn(entity.getNameEn());

        }
        return dto;

    }

}
