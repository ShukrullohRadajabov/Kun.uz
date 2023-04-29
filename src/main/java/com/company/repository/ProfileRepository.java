package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmailAndPasswordAndVisible(String email, String password, boolean visible);

    Page<ProfileEntity> findAll(Pageable paging);
}
