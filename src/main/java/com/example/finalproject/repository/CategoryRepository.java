package com.example.finalproject.repository;

import com.example.finalproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public Optional<Category> findByName(String name);
}
