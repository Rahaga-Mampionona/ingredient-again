package com.ingredient.rahaga.service;

public class DishService {
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public void updateDishIngredients(Integer dishId, List<Ingredient> ingredients) {
        dishRepository.updateIngredients(dishId, ingredients);
    }
}
