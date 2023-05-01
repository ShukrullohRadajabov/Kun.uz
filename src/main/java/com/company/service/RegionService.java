package com.company.service;

import com.company.dto.region.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        isValidRegion(dto);
        entity.setNameUZ(dto.getNameUZ());
        entity.setNameRU(dto.getNameRU());
        entity.setNameEN(dto.getNameEN());
        regionRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(Boolean.TRUE);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidRegion(RegionDTO dto) {
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

    public RegionDTO update(Integer id, RegionDTO dto) {
        isValidRegion(dto);
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Region is empty");
        }
        RegionEntity entity = optional.get();
        entity.setNameUZ(dto.getNameUZ());
        entity.setNameRU(dto.getNameRU());
        entity.setNameEN(dto.getNameEN());
        regionRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(Boolean.TRUE);
        dto.setId(entity.getId());
        return dto;
    }

    public boolean deleteProfile(Integer id) {
        Optional<RegionEntity> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Region not found");
        }
        regionRepository.deleteById(id);
        return true;
    }

    public RegionEntity get(Integer moderId) {
        Optional<RegionEntity> byId = regionRepository.findById(moderId);
        return null;
    }

    public List<RegionDTO> getList() {
        List<RegionDTO> dtoList = new LinkedList<>();
        Iterable<RegionEntity> regionList = regionRepository.findAll();
        for (RegionEntity entity : regionList) {
            RegionDTO dto = new RegionDTO();
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

   /* public List<RegionDTO> getByLang(String lang) {
        List<RegionDTO> dtoList = new LinkedList<>();
        switch (lang) {
            case "nameUZ" -> {
                List<RegionEntity> allByNameUZ = regionRepository.findAllByNameUZ();
                if(!allByNameUZ.isEmpty()){
                    for (RegionEntity entity : allByNameUZ) {
                        RegionDTO dto = new RegionDTO();
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
                List<RegionEntity> allByNameEN = regionRepository.findAllByNameEN();
                if(!allByNameEN.isEmpty()){
                    for (RegionEntity entity : allByNameEN) {
                        RegionDTO dto = new RegionDTO();
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
                List<RegionEntity> allByNameRU = regionRepository.findAllByNameRU();
                if(!allByNameRU.isEmpty()){
                    for (RegionEntity entity : allByNameRU) {
                        RegionDTO dto = new RegionDTO();
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
                List<String> listUz = regionRepository.findAllByNameUzOrderByCreatedDate();
                return listUz;
            case "nameRU":
                List<String> listRu = regionRepository.findAllByNameRuOrderByCreatedDate();
                return listRu;
            case "nameEN":
                List<String> listEng = regionRepository.findAllByNameEngOrderByCreatedDate();
                return listEng;
        }
        return null;
    }
}
