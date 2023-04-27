package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralStatus;
import com.company.exceptions.AppBadRequestException;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto, Integer adminId){
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

    public void isValidProfile(ProfileDTO dto){
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
}
