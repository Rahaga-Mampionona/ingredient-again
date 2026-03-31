package com.ingredient.rahaga.controller;

import
import com.ingredient.rahaga.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class IngredientController {
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    public <Ingredient> ResponseEntity<Object> IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    public IngredientController(IngredientService ingredientService) {
            this.ingredientService = ingredientService;
        }

        @GetMapping
        public List<Ingredient> getAllIngredients() {
            return ingredientRepository.findAll();
            return ingredientService.getAllIngredients();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) {
            try {
                Ingredient ingredient = ingredientRepository.findById(id);
                return ResponseEntity.ok(ingredient);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Ingredient ingredient = ingredientService.getIngredientById(id);
            return ResponseEntity.ok(ingredient);
        }

        @GetMapping("/{id}/stock")
        @@ -46,11 +41,7 @@ public ResponseEntity<StockValue> getStock(
        return ResponseEntity.badRequest().build();
    }

        try {
        StockValue stock = ingredientRepository.getStockValueAt(id, at, unit);
        return ResponseEntity.ok(stock);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    StockValue stock = ingredientService.getStockValueAt(id, at, unit);
        return ResponseEntity.ok(stock);
}
}
