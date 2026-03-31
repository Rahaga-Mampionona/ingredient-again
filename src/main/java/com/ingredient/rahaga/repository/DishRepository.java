package com.ingredient.rahaga.repository;

import com.ingredient.rahaga.datasource.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

                String typeStr = rs.getString("dish_type");
                if ("STARTER".equals(typeStr)) {
                    typeStr = "START";
                }
                dish.setDishType(DishTypeEnum.valueOf(typeStr));

                if (rs.getObject("selling_price") != null) {
                    dish.setSellingPrice(rs.getDouble("selling_price"));
                }
                dish.setIngredients(loadIngredientsForDish(conn, dish.getId()));

                dish.setIngredients(loadIngredientsForDish(dish.getId()));

                dishes.add(dish);
            }
        } catch (SQLException e) {
            @@ -43,7 +51,7 @@
            return dishes;
        }

        private List<Ingredient> loadIngredientsForDish(Connection conn, Integer dishId) throws SQLException {
            private List<Ingredient> loadIngredientsForDish(Integer dishId) {
                List<Ingredient> ingredients = new ArrayList<>();
                String sql = """
            SELECT i.id, i.name, i.price, i.category
@@ -53,7 +61,9 @@
            ORDER BY i.id
            """;

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    try (Connection conn = dataSource.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(sql)) {

                        stmt.setInt(1, dishId);
                        try (ResultSet rs = stmt.executeQuery()) {
                            while (rs.next()) {
                                @@ -65,6 +75,8 @@
                                        ingredients.add(ing);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException("Erreur lors du chargement des ingrédients du plat id=" + dishId, e);
                    }
                    return ingredients;
                }
                @@ -82,7 +94,11 @@
                        deleteStmt.executeUpdate();
            }

            String insertSql = "INSERT INTO dish_ingredient (id_dish, id_ingredient, quantity_required, unit) VALUES (?, ?, 0.0, 'KG')";
            String insertSql = """
                INSERT INTO dish_ingredient (id_dish, id_ingredient, quantity_required, unit)
                VALUES (?, ?, 0.0, 'KG')
                """;

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                for (Ingredient ing : ingredientsToSet) {
                    if (ing.getId() != null && ingredientExists(conn, ing.getId())) {
                        @@ -94,7 +110,7 @@
                    }
                    conn.commit();
                } catch (SQLException e) {
                    throw new RuntimeException("Erreur lors de la mise à jour des ingrédients du plat", e);
                    throw new RuntimeException("Erreur lors de la mise à jour des ingrédients du plat id=" + dishId, e);
                }
}
