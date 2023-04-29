package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        isValidRegion(dto);
        entity.setNameUZ(dto.getNameUZ());
        entity.setNameRU(dto.getNameRU());
        entity.setNameEN(dto.getNameEN());
        categoryRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(Boolean.TRUE);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidRegion(CategoryDTO dto) {
        if (dto.getNameUZ() == null || dto.getNameUZ().isBlank()) {
            throw new AppBadRequestException("Name uz qani?");
        }
        if (dto.getNameRU() == null || dto.getNameRU().isBlank()) {
            throw new AppBadRequestException("Name ru qani?");
        }
        if (dto.getNameEN() == null || dto.getNameEN().isBlank()) {
            throw new AppBadRequestException("Name Eng qani?");
        }
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        isValidRegion(dto);
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Region is empty");
        }
        CategoryEntity entity = optional.get();
        entity.setNameUZ(dto.getNameUZ());
        entity.setNameRU(dto.getNameRU());
        entity.setNameEN(dto.getNameEN());
        categoryRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(Boolean.TRUE);
        dto.setId(entity.getId());
        return dto;
    }

    public boolean deleteProfile(Integer id) {
        Optional<CategoryEntity> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Region not found");
        }
        categoryRepository.deleteById(id);
        return true;
    }

    public CategoryEntity get(Integer moderId) {
        Optional<CategoryEntity> byId = categoryRepository.findById(moderId);
        return null;
    }

    public List<CategoryDTO> getList() {
        List<CategoryDTO> dtoList = new LinkedList<>();
        Iterable<CategoryEntity> regionList = categoryRepository.findAll();
        for (CategoryEntity entity : regionList) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setNameUZ(entity.getNameUZ());
            dto.setNameRU(entity.getNameRU());
            dto.setNameEN(entity.getNameEN());
            dto.setVisible(entity.getVisible());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

   /* public List<CategoryDTO> getByLang(String lang) {
        List<CategoryDTO> dtoList = new LinkedList<>();
        switch (lang) {
            case "nameUZ" -> {
                List<CategoryEntity> allByNameUZ = categoryRepository.findAllByNameUZ();
                if(!allByNameUZ.isEmpty()){
                    for (CategoryEntity entity : allByNameUZ) {
                        CategoryDTO dto = new CategoryDTO();
                        dto.setId(entity.getId());
                        dto.setName(entity.getNameUZ());
                        dto.setVisible(entity.getVisible());
                        dto.setCreatedDate(entity.getCreatedDate());
                        dtoList.add(dto);
                    }
                }
                else throw new MethodNotAllowedException("Name Uz list empty");
                return dtoList;
            }
            case "nameEN" -> {
                List<CategoryEntity> allByNameEN = categoryRepository.findAllByNameEN();
                if(!allByNameEN.isEmpty()){
                    for (CategoryEntity entity : allByNameEN) {
                        CategoryDTO dto = new CategoryDTO();
                        dto.setId(entity.getId());
                        dto.setName(entity.getNameUZ());
                        dto.setVisible(entity.getVisible());
                        dto.setCreatedDate(entity.getCreatedDate());
                        dtoList.add(dto);
                    }
                }
                else throw new MethodNotAllowedException("Name Ru list empty");
                return dtoList;
            }
            case "nameRU" -> {
                List<CategoryEntity> allByNameRU = categoryRepository.findAllByNameRU();
                if(!allByNameRU.isEmpty()){
                    for (CategoryEntity entity : allByNameRU) {
                        CategoryDTO dto = new CategoryDTO();
                        dto.setId(entity.getId());
                        dto.setName(entity.getNameUZ());
                        dto.setVisible(entity.getVisible());
                        dto.setCreatedDate(entity.getCreatedDate());
                        dtoList.add(dto);
                    }
                }
                else throw new MethodNotAllowedException("Name En list empty");
                return dtoList;
            }
            default -> throw new AppBadRequestException("Wrong language name");
        }
    }*/


    public List<String> getByLang(String lang) {
        switch (lang){
            case "nameUZ":
                List<String> listUz = categoryRepository.findAllByNameUzOrderByCreatedDate();
                return listUz;
            case "nameRU":
                List<String> listRu = categoryRepository.findAllByNameRuOrderByCreatedDate();
                return listRu;
            case "nameEN":
                List<String> listEng = categoryRepository.findAllByNameEngOrderByCreatedDate();
                return listEng;
        }
        return null;
    }
}
