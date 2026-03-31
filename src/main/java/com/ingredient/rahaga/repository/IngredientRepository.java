package com.ingredient.rahaga.repository;

public class IngredientRepository {
    private final DataSource dataSource;

    public IngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT id, name, price, category FROM ingredient ORDER BY id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setId(rs.getInt("id"));
                ing.setName(rs.getString("name"));
                ing.setPrice(rs.getDouble("price"));
                ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                ingredients.add(ing);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des ingrédients", e);
        }
        return ingredients;
    }

    public Ingredient findById(Integer id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ingredient ing = new Ingredient();
                    ing.setId(rs.getInt("id"));
                    ing.setName(rs.getString("name"));
                    ing.setPrice(rs.getDouble("price"));
                    ing.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                    return ing;
                } else {
                    throw new RuntimeException("Ingredient.id=" + id + " is not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'ingrédient", e);
            throw new RuntimeException("Erreur lors de la recherche de l'ingrédient id=" + id, e);
        }
    }

    @@ -85,7 +85,7 @@
            return new StockValue(quantity, unit);
}
        } catch (SQLException e) {
        throw new RuntimeException("Erreur lors du calcul du stock", e);
            throw new RuntimeException("Erreur lors du calcul du stock de l'ingrédient id=" + ingredientId, e);
        }
                }
                }
}
