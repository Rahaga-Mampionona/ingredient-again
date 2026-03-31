package com.ingredient.rahaga.service;


import com.ingredient.rahaga.dto.DishCreateRequest;
import com.ingredient.rahaga.entity.Dish;
import com.ingredient.rahaga.entity.DishTypeEnum;
import com.ingredient.rahaga.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }


    public List<Dish> createDishes(List<DishCreateRequest> requests) {
        List<Dish> dishes = new ArrayList<>();

        for (DishCreateRequest req : requests) {

            if (dishRepository.existsByName(req.getName())) {
                throw new IllegalArgumentException(
                        "Dish.name=" + req.getName() + " already exists"
                );
            }


            Dish dish = new Dish();
            dish.setName(req.getName());
            dish.setDishType(req.getDishType() != null ? req.getDishType() : DishTypeEnum.STARTER);
            dish.setSellingPrice(req.getSellingPrice() != null ? req.getSellingPrice() : 0.0);

            dishes.add(dish);
        }

        return dishRepository.saveAll(dishes);
    }


    public List<Dish> getFilteredDishes(Double priceUnder, Double priceOver, String name) {
        return dishRepository.findWithFilters(priceUnder, priceOver, name);
    }
}