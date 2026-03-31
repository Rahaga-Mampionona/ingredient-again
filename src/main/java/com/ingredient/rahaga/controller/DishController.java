package com.ingredient.rahaga.controller;

import com.ingredient.rahaga.dto.DishCreateRequest;
import com.ingredient.rahaga.entity.Dish;
import com.ingredient.rahaga.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }


    @GetMapping
    public ResponseEntity<List<Dish>> getDishes(
            @RequestParam(required = false) Double priceUnder,
            @RequestParam(required = false) Double priceOver,
            @RequestParam(required = false) String name
    ) {
        try {
            List<Dish> dishes = dishService.getFilteredDishes(priceUnder, priceOver, name);
            return ResponseEntity.ok(dishes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


    @PostMapping
    public ResponseEntity<?> createDishes(@RequestBody List<DishCreateRequest> dishes) {
        try {
            List<Dish> created = dishService.createDishes(dishes);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}