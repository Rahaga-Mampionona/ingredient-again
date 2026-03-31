package com.ingredient.rahaga.service;
import com.ingredient.rahaga.entity.Ingredient;
import com.ingredient.rahaga.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    
    public Ingredient getIngredientById(Integer id) {
        return ingredientRepository.findById(id);
    }
}