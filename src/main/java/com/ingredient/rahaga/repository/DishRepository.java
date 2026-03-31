package com.ingredient.rahaga.repository;
import com.ingredient.rahaga.datasource.DataSource;
import com.ingredient.rahaga.entity.CategoryEnum;
import com.ingredient.rahaga.entity.Dish;
import com.ingredient.rahaga.entity.DishTypeEnum;
import com.ingredient.rahaga.entity.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishRepository {

    private final DataSource dataSource;

    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT id, name, dish_type, selling_price FROM dish ORDER BY id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                dish.setSellingPrice(rs.getDouble("selling_price"));
                dish.setIngredients(loadIngredientsForDish(conn, dish.getId()));
                dishes.add(dish);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des plats", e);
        }

        return dishes;
    }

    private List<Ingredient> loadIngredientsForDish(Connection conn, Integer dishId) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT i.id, i.name, i.price, i.category FROM ingredient i " +
                "JOIN dish_ingredient di ON i.id = di.id_ingredient WHERE di.id_dish = ? ORDER BY i.id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dishId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ingredient ing = new Ingredient();
                    ing.setId(rs.getInt("id"));
                    ing.setName(rs.getString("name"));
                    ing.setPrice(rs.getDouble("price"));
                    ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                    ingredients.add(ing);
                }
            }
        }

        return ingredients;
    }

    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM dish WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du nom du plat", e);
        }

        return false;
    }

    public List<Dish> saveAll(List<Dish> dishes) {
        String sql = "INSERT INTO dish(name, dish_type, selling_price) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection()) {
            for (Dish dish : dishes) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, dish.getName());
                    stmt.setString(2, dish.getDishType().name());
                    stmt.setDouble(3, dish.getSellingPrice());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            dish.setId(rs.getInt("id"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement des plats", e);
        }

        return dishes;
    }

    public List<Dish> findWithFilters(Double priceUnder, Double priceOver, String name) {
        List<Dish> dishes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, name, dish_type, selling_price FROM dish WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (priceUnder != null) {
            sql.append(" AND selling_price < ?");
            params.add(priceUnder);
        }
        if (priceOver != null) {
            sql.append(" AND selling_price > ?");
            params.add(priceOver);
        }
        if (name != null) {
            sql.append(" AND LOWER(name) LIKE ?");
            params.add("%" + name.toLowerCase() + "%");
        }

        sql.append(" ORDER BY id");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Dish dish = new Dish();
                    dish.setId(rs.getInt("id"));
                    dish.setName(rs.getString("name"));
                    dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                    dish.setSellingPrice(rs.getDouble("selling_price"));
                    dishes.add(dish);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des plats filtrés", e);
        }

        return dishes;
    }
}