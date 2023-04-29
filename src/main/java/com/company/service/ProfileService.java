package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralStatus;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        isValidProfile(dto);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setStatus(GeneralStatus.ACTIVE);
        entity.setPrtId(null);
        profileRepository.save(entity);
        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public ProfileDTO create(ProfileDTO dto, Integer adminId) {
        ProfileEntity entity = new ProfileEntity();
        isValidProfile(dto);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setStatus(GeneralStatus.ACTIVE);
        entity.setPrtId(adminId);
        profileRepository.save(entity);
        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("Name qani?");
        }
        if (dto.getSurname() == null || dto.getSurname().isBlank()) {
            throw new AppBadRequestException("Surname qani?");
        }
        if (dto.getRole() == null) {
            throw new AppBadRequestException("Age qani?");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new AppBadRequestException("Email qani?");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new AppBadRequestException("Password qani?");
        }
        if (dto.getPhone() == null || dto.getPhone().isBlank()) {
            throw new AppBadRequestException("Phone qani?");
        }
    }


    public ProfileDTO update(Integer id, ProfileDTO dto) {
        isValidProfile(dto);
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Profile is empty");
        }
        ProfileEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(entity); // save profile
        dto.setId(entity.getId());
        return dto;
    }

    public ProfileEntity get(Integer moderId) {
        Optional<ProfileEntity> byId = profileRepository.findById(moderId);

        return null;
    }

    public Page<ProfileDTO> pagination(int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<ProfileEntity> pageObj = profileRepository.findAll(paging);

        Long totalCount = pageObj.getTotalElements();

        List<ProfileEntity> entityList = pageObj.getContent();
        List<ProfileDTO> dtoList = new LinkedList<>();

        for (ProfileEntity entity : entityList) {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dtoList.add(dto);
        }
        Page<ProfileDTO> response = new PageImpl<ProfileDTO>(dtoList, paging, totalCount);
        return response;
    }

    public boolean deleteProfile(Integer id) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Profile not found");
        }
        profileRepository.deleteById(id);
        return true;
    }
}
