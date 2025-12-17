package ai.novasuite.demo.ui.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Optional: eigene Suchmethoden wie
    // List<Recipe> findByTitleContainingIgnoreCase(String title);
}
