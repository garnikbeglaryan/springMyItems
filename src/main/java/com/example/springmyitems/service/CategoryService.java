package com.example.springmyitems.service;

import com.example.springmyitems.entity.Category;
import com.example.springmyitems.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;


    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(int id) {
        return categoryRepository.getById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
