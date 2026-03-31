package com.ingredient.rahaga.controller;

import com.ingredient.rahaga.repository.DishRepository;
import com.ingredient.rahaga.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class DishController {
    private final DishRepository dishRepository;
    private final DishService dishService;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    public DishController(DishService dishService) {
            this.dishService = dishService;
        }

        @GetMapping
        public List<Dish> getAllDishes() {
            return dishRepository.findAll();
            return dishService.getAllDishes();
        }

        @PutMapping("/{id}/ingredients")
        @@ -33,11 +32,7 @@ public ResponseEntity<Void> updateDishIngredients(
        return ResponseEntity.badRequest().build();
    }

        try {
        dishRepository.updateIngredients(id, ingredients);
        return ResponseEntity.ok().build();
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
        dishService.updateDishIngredients(id, ingredients);
        return ResponseEntity.ok().build();
}
}
}
