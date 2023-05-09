package com.company.service;

import com.company.dto.auth.AuthDTO;
import com.company.dto.auth.AuthResponseDTO;
import com.company.dto.auth.RegistrationDTO;
import com.company.dto.auth.RegistrationResponseDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralStatus;
import com.company.enums.ProfileRole;
import com.company.exceptions.AlreadyExistException;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private ProfileRepository profileRepository;
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

    public ProfileDTO registration(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
//        isValidProfile(dto);
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

    public RegistrationResponseDTO registration(RegistrationDTO dto){
        isValidProfile(dto);
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if(optional.isPresent()){
            throw new AlreadyExistException("Email already exist");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(ProfileRole.USER);
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTER);
        profileRepository.save(entity);
        mailSenderService.sendEmail(dto.getEmail(), "Registration", "Mazgi qaliysan");
        String s = "Verification link was sent to email: "+dto.getEmail();
        return new RegistrationResponseDTO(s);
    }





    public void isValidProfile(RegistrationDTO dto){
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
