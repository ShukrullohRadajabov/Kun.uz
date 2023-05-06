package com.company.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleCustomRepository {
    @Autowired
    private EntityManager entityManager;


}
