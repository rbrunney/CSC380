package com.example.excercise11;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface CategoryJPARepository extends JpaRepository<Category, Integer> {
}
