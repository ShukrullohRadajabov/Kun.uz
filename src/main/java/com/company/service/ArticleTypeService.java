package com.company.service;

import com.company.dto.ArticleTypeDTO;
import com.company.entity.ArticleTypeEntity;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ArticleTypeRepository;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        isValidRegion(dto);
        entity.setNameUZ(dto.getNameUZ());
        entity.setNameRU(dto.getNameRU());
        entity.setNameEN(dto.getNameEN());
        articleTypeRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(Boolean.TRUE);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidRegion(ArticleTypeDTO dto) {
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

    public ArticleTypeDTO update(Integer id, ArticleTypeDTO dto) {
        isValidRegion(dto);
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Region is empty");
        }
        ArticleTypeEntity entity = optional.get();
        entity.setNameUZ(dto.getNameUZ());
        entity.setNameRU(dto.getNameRU());
        entity.setNameEN(dto.getNameEN());
        articleTypeRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(Boolean.TRUE);
        dto.setId(entity.getId());
        return dto;
    }

    public boolean deleteProfile(Integer id) {
        Optional<ArticleTypeEntity> byId = articleTypeRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Region not found");
        }
        articleTypeRepository.deleteById(id);
        return true;
    }

    public ArticleTypeEntity get(Integer moderId) {
        Optional<ArticleTypeEntity> byId = articleTypeRepository.findById(moderId);
        return null;
    }

    public List<ArticleTypeDTO> getList() {
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        Iterable<ArticleTypeEntity> regionList = articleTypeRepository.findAll();
        for (ArticleTypeEntity entity : regionList) {
            ArticleTypeDTO dto = new ArticleTypeDTO();
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

   /* public List<ArticleTypeDTO> getByLang(String lang) {
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        switch (lang) {
            case "nameUZ" -> {
                List<ArticleTypeEntity> allByNameUZ = articleTypeRepository.findAllByNameUZ();
                if(!allByNameUZ.isEmpty()){
                    for (ArticleTypeEntity entity : allByNameUZ) {
                        ArticleTypeDTO dto = new ArticleTypeDTO();
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
                List<ArticleTypeEntity> allByNameEN = articleTypeRepository.findAllByNameEN();
                if(!allByNameEN.isEmpty()){
                    for (ArticleTypeEntity entity : allByNameEN) {
                        ArticleTypeDTO dto = new ArticleTypeDTO();
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
                List<ArticleTypeEntity> allByNameRU = articleTypeRepository.findAllByNameRU();
                if(!allByNameRU.isEmpty()){
                    for (ArticleTypeEntity entity : allByNameRU) {
                        ArticleTypeDTO dto = new ArticleTypeDTO();
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
                List<String> listUz = articleTypeRepository.findAllByNameUzOrderByCreatedDate();
                return listUz;
            case "nameRU":
                List<String> listRu = articleTypeRepository.findAllByNameRuOrderByCreatedDate();
                return listRu;
            case "nameEN":
                List<String> listEng = articleTypeRepository.findAllByNameEngOrderByCreatedDate();
                return listEng;
        }
        return null;
    }
}
