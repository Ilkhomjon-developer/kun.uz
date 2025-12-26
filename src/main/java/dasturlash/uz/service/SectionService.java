package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.SectionEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.mapper.SectionMapper;
import dasturlash.uz.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;


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
        return dto;
    }

    public SectionDTO update(Long id, SectionDTO dto){

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

    public Boolean delete(Long id){
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
            dtoList.add(dto);

        }

        return new PageImpl<>(dtoList, pageRequest, total);
    }

    public List<SectionDTO> getAllByLang(AppLanguageEnum lang){

        Iterable<SectionMapper> entityList = sectionRepository.findAllByLanguageAndVisibleIsTrue(lang.name());
        List<SectionDTO> dtoList = new ArrayList<>();

        entityList.forEach(mapper -> {
            SectionDTO dto = new SectionDTO();
            dto.setId(mapper.getId());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setSectionKey(mapper.getSectionKey());
            dto.setName(mapper.getName());

            dtoList.add(dto);
        });

        return dtoList;
    }

    private SectionDTO toLanguageResponseDTO(SectionMapper mapper) {

        SectionDTO dto = new SectionDTO();
        dto.setId(mapper.getId());
        dto.setOrderNumber(mapper.getOrderNumber());
        dto.setSectionKey(mapper.getSectionKey());
        dto.setName(mapper.getName());

        return dto;

    }

    public List<SectionDTO> getSectionListByArticleIdAndLang(Long id, AppLanguageEnum lang) {
        List<SectionMapper> iterable = sectionRepository.getSectionListByArticleIdAndLang(id, lang.name());
        List<SectionDTO> dtoList = new LinkedList<>();
        iterable.forEach(mapper -> {
            SectionDTO dto = new SectionDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setSectionKey(mapper.getSectionKey());
            dtoList.add(dto);
        });
        return dtoList;
    }

}
