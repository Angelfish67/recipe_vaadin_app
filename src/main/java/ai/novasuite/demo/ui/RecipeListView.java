package ai.novasuite.demo.ui;

import ai.novasuite.demo.ui.recipe.Recipe;
import ai.novasuite.demo.ui.recipe.RecipeService;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@PageTitle("Rezepte")
@Route(value = "view-recipes", layout = MainLayout.class)
public class RecipeListView extends VerticalLayout {

    private final RecipeService recipeService;

    private final TextField search = new TextField("Suche");
    private final Button refresh = new Button("Aktualisieren");
    private final Button create = new Button("Neu erstellen");

    private final Grid<Recipe> grid = new Grid<>(Recipe.class, false);

    public RecipeListView(RecipeService recipeService) {
        this.recipeService = Objects.requireNonNull(recipeService);

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Alle Rezepte"));

        configureToolbar();
        configureGrid();

        add(buildToolbar(), grid);

        loadData();
    }

    private void configureToolbar() {
        search.setPlaceholder("Titel, Beschreibung, Zutaten...");
        search.setClearButtonVisible(true);
        search.setWidthFull();
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.addValueChangeListener(e -> loadData());

        refresh.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        refresh.addClickListener(e -> loadData());

        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        create.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("create")));
    }

    private HorizontalLayout buildToolbar() {
        HorizontalLayout bar = new HorizontalLayout(search, refresh, create);
        bar.setWidthFull();
        bar.setAlignItems(Alignment.END);
        bar.expand(search);
        return bar;
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addColumn(Recipe::getTitle)
                .setHeader("Titel")
                .setAutoWidth(true)
                .setFlexGrow(1);

        grid.addColumn(r -> safeShort(r.getDescription(), 80))
                .setHeader("Beschreibung")
                .setAutoWidth(false)
                .setFlexGrow(2);

        grid.addColumn(r -> Objects.toString(r.getServings(), ""))
                .setHeader("Portionen")
                .setAutoWidth(true)
                .setFlexGrow(0);

        grid.addComponentColumn(r -> {
                    Button view = new Button("Ansehen");
                    view.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                    view.addClickListener(e -> openDetailsDialog(r));

                    Button delete = new Button("Löschen");
                    delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
                    delete.addClickListener(e -> handleDelete(r));

                    HorizontalLayout actions = new HorizontalLayout(view, delete);
                    actions.setPadding(false);
                    actions.setSpacing(true);
                    return actions;
                })
                .setHeader("Aktionen")
                .setAutoWidth(true)
                .setFlexGrow(0);

        grid.addItemDoubleClickListener(e -> openDetailsDialog(e.getItem()));
    }

    private void loadData() {
        List<Recipe> all = safeList(recipeService.findAll());
        String q = normalize(search.getValue());

        if (q.isBlank()) {
            grid.setItems(all);
            return;
        }

        List<Recipe> filtered = new ArrayList<>();
        for (Recipe r : all) {
            if (matches(r, q)) filtered.add(r);
        }
        grid.setItems(filtered);
    }

    private boolean matches(Recipe r, String q) {
        return normalize(r.getTitle()).contains(q)
                || normalize(r.getDescription()).contains(q)
                || normalize(r.getIngredients()).contains(q)
                || normalize(r.getInstructions()).contains(q);
    }

    private void handleDelete(Recipe r) {
        try {
            recipeService.delete(r);
            Notification.show("Rezept gelöscht.", 2500, Notification.Position.TOP_CENTER);
            loadData();
        } catch (Exception ex) {
            Notification.show("Löschen fehlgeschlagen: " + ex.getMessage(), 4000, Notification.Position.MIDDLE);
        }
    }

    private void openDetailsDialog(Recipe r) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Rezept ansehen");

        TextField title = new TextField("Titel");
        title.setValue(Objects.toString(r.getTitle(), ""));
        title.setReadOnly(true);
        title.setWidthFull();

        TextArea description = new TextArea("Beschreibung");
        description.setValue(Objects.toString(r.getDescription(), ""));
        description.setReadOnly(true);
        description.setWidthFull();
        description.setMaxHeight("220px");

        TextArea ingredients = new TextArea("Zutaten");
        ingredients.setValue(Objects.toString(r.getIngredients(), ""));
        ingredients.setReadOnly(true);
        ingredients.setWidthFull();
        ingredients.setMaxHeight("260px");

        TextArea instructions = new TextArea("Zubereitung");
        instructions.setValue(Objects.toString(r.getInstructions(), ""));
        instructions.setReadOnly(true);
        instructions.setWidthFull();
        instructions.setMaxHeight("320px");

        TextField servings = new TextField("Portionen");
        servings.setValue(Objects.toString(r.getServings(), ""));
        servings.setReadOnly(true);

        FormLayout form = new FormLayout(title, description, ingredients, instructions, servings);
        form.setWidthFull();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("900px", 2)
        );
        form.setColspan(title, 2);
        form.setColspan(description, 2);
        form.setColspan(ingredients, 2);
        form.setColspan(instructions, 2);

        dialog.add(form);

        Button close = new Button("Schliessen", e -> dialog.close());
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialog.getFooter().add(close);

        dialog.setWidth("900px");
        dialog.open();
    }

    private static String safeShort(String s, int max) {
        String v = Objects.toString(s, "");
        if (v.length() <= max) return v;
        return v.substring(0, Math.max(0, max - 1)) + "…";
    }

    private static String normalize(String s) {
        return Objects.toString(s, "")
                .trim()
                .toLowerCase(Locale.ROOT);
    }

    private static <T> List<T> safeList(List<T> list) {
        return list != null ? list : List.of();
    }
}
