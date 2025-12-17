package ai.novasuite.demo.ui;

import ai.novasuite.demo.ui.recipe.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Neues Rezept")

@Route(value = "create", layout = MainLayout.class) // MainLayout hast du ja schon
public class RecipeCreateView extends VerticalLayout {

    private final RecipeService recipeService;

    private final TextField title = new TextField("Titel");
    private final TextArea description = new TextArea("Beschreibung");
    private final TextArea ingredients = new TextArea("Zutaten (eine pro Zeile)");
    private final TextArea instructions = new TextArea("Zubereitung");
    private final IntegerField servings = new IntegerField("Portionen");

    private final Button save = new Button("Speichern");
    private final Button reset = new Button("Zurücksetzen");

    public RecipeCreateView(RecipeService recipeService) {
        this.recipeService = recipeService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new Text("Neues Rezept erstellen"));

        description.setMaxHeight("200px");
        ingredients.setMaxHeight("250px");
        instructions.setMaxHeight("300px");
        servings.setMin(1);
        servings.setValue(2);

        FormLayout form = new FormLayout();
        form.add(
                title,
                description,
                ingredients,
                instructions,
                servings
        );
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("800px", 2)
        );

        save.getElement().getThemeList().add("primary");
        save.addClickListener(e -> handleSave());

        reset.addClickListener(e -> clearForm());

        add(form, save, reset);
    }

    private void handleSave() {
        if (title.isEmpty()) {
            Notification.show("Titel darf nicht leer sein.", 3000, Notification.Position.MIDDLE);
            return;
        }

        Recipe r = new Recipe();
        r.setTitle(title.getValue());
        r.setDescription(description.getValue());
        r.setIngredients(ingredients.getValue());
        r.setInstructions(instructions.getValue());
        r.setServings(servings.getValue() != null ? servings.getValue() : 1);

        recipeService.save(r);

        Notification.show("Rezept gespeichert!", 3000, Notification.Position.TOP_CENTER);
        clearForm();
    }

    private void clearForm() {
        title.clear();
        description.clear();
        ingredients.clear();
        instructions.clear();
        servings.setValue(2);
    }
}
