package com.ingredient.rahaga.service;

import org.springframework.format.annotation.DurationFormat;
import org.springframework.stereotype.Service;
import

import java.time.Instant;


public class IngredientService {

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

        public StockValue getStockValueAt(Integer ingredientId, Instant at, DurationFormat.Unit unit) {
            return ingredientRepository.getStockValueAt(ingredientId, at, unit);
        }
}
