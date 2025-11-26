package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.entity.CategoryEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.exps.AppBadException;
import dasturlash.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto){

        Optional<CategoryEntity> optional = categoryRepository.findByCategoryKey(dto.getCategoryKey());

        if(optional.isPresent()){
            throw new AppBadException("Category already exist");
        }

        CategoryEntity entity = new CategoryEntity();

        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setCategoryKey(dto.getCategoryKey());

        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto){
        Optional<CategoryEntity> optional = categoryRepository.findByIdAndVisibleIsTrue(id);
        if(optional.isEmpty()){
            throw new AppBadException("Category not found");
        }

        Optional<CategoryEntity> keyOptional = categoryRepository.findByCategoryKey(dto.getCategoryKey());

        if(keyOptional.isPresent() && !id.equals(keyOptional.get().getId())){
            throw new AppBadException("Category key already exist");
        }

        CategoryEntity entity = optional.get();

        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setCategoryKey(dto.getCategoryKey());
        entity.setOrderNumber(dto.getOrderNumber());

        categoryRepository.save(entity);

        return dto;

    }

    public Boolean delete(Integer id){

      return categoryRepository.updateVisibleById(id, false) == 1;
    }

    public List<CategoryDTO> getAll(){

        Iterable<CategoryEntity> entityList = categoryRepository.getAllByVisibleIsTrue();
        List<CategoryDTO> dtoList = new ArrayList<>();

        for (CategoryEntity entity : entityList) {

            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setNameUz(entity.getNameUz());
            dto.setNameEn(entity.getNameEn());
            dto.setNameRu(entity.getNameRu());
            dto.setCategoryKey(entity.getCategoryKey());
            dto.setCreatedDate(entity.getCreatedDate());

            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<CategoryDTO> getAllByLang(AppLanguageEnum lang) {

        Iterable<CategoryEntity> iterable = categoryRepository.getAllByVisibleIsTrue();
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(entity, lang)));

        return dtos;
    }

    private CategoryDTO toLangResponseDto(CategoryEntity entity, AppLanguageEnum lang) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setCategoryKey(entity.getCategoryKey());
        dto.setOrderNumber(entity.getOrderNumber());

        switch (lang) {
            case UZ ->dto.setName(entity.getNameUz());

            case RU ->dto.setName(entity.getNameRu());

            case EN ->dto.setName(entity.getNameEn());
        }
        return dto;
    }
}
