package com.company.service;

import com.company.dto.AuthDTO;
import com.company.dto.AuthResponseDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralStatus;
import com.company.enums.ProfileRole;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    ProfileRepository profileRepository;
    public AuthResponseDTO login(AuthDTO dto){
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisible(dto.getEmail(), MD5Util.getMd5Hash(dto.getPassword()), true);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email or password incorrect");
        }
        ProfileEntity entity = optional.get();
        if(!entity.getStatus().equals(GeneralStatus.ACTIVE)){
            throw new AppBadRequestException("Wrong status");
        }
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setName(entity.getName());
        authResponseDTO.setSurname(entity.getSurname());
        authResponseDTO.setRole(entity.getRole());
        authResponseDTO.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return authResponseDTO;
    }

    public Object registration(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        isValidProfile(dto);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setRole(ProfileRole.USER);
        entity.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(entity);
        dto.setPassword(null);
        return dto;
    }




    public void isValidProfile(ProfileDTO dto){
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("Name qani?");
        }
        if (dto.getSurname() == null || dto.getSurname().isBlank()) {
            throw new AppBadRequestException("Surname qani?");
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
}
