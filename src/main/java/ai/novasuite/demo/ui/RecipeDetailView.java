package ai.novasuite.demo.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Ecommerce")
@Route(value = "recipe", layout = MainLayout.class)
@CssImport("./styles/main.css")
public class RecipeDetailView extends VerticalLayout implements HasUrlParameter<String> {

    private final Div content = new Div();

    private String slug = "ratatouille";
    private String title = "Ratatouille";
    private String timeText = "20 Minuten";
    private String heroImage = "/images/ratatouille1.png";

    public RecipeDetailView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.STRETCH);

        add(buildHeader(), content);
        showIngredients(); // Default-Tab
    }

    private Component buildHeader() {
        VerticalLayout header = new VerticalLayout();
        header.setPadding(false);
        header.setSpacing(false);
        header.setAlignItems(Alignment.STRETCH);

        HorizontalLayout topBar = new HorizontalLayout();
        Button back = new Button(new Icon(VaadinIcon.ANGLE_LEFT), e -> getUI().ifPresent(ui -> ui.navigate("")));
        back.getStyle().set("border-radius", "999px");
        topBar.add(back);

        Image img = new Image(heroImage, "Hero");
        img.setWidth("100%");
        img.getStyle().set("object-fit","cover");
        img.getStyle().set("height","220px");
        img.getStyle().set("border-bottom","1px solid #e9e9e9");

        HorizontalLayout titleRow = new HorizontalLayout();
        titleRow.setWidthFull();
        titleRow.setAlignItems(Alignment.CENTER);
        titleRow.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        H3 name = new H3(title);
        name.getStyle().set("margin","8px 0");
        Span time = new Span(timeText);
        time.getStyle().set("color","#333");
        time.getStyle().set("font-size","12px");

        titleRow.add(name, time);

        // Tabs
        HorizontalLayout tabs = new HorizontalLayout();
        tabs.setWidthFull();
        tabs.getStyle().set("border-top","1px solid #ddd");
        tabs.getStyle().set("border-bottom","1px solid #ddd");

        Button tabIngredients = new Button("Zutaten", e -> showIngredients());
        Button tabSteps = new Button("Zubereitung", e -> showSteps());
        for (Button b : new Button[]{tabIngredients, tabSteps}) {
            b.getStyle().set("border-radius","0");
            b.getStyle().set("flex","1");
            b.getStyle().set("background","#efefef");
        }
        tabs.add(tabIngredients, tabSteps);

        header.add(topBar, img, titleRow, tabs);
        return header;
    }

    private void showIngredients() {
        content.removeAll();
        content.getStyle().set("padding","12px 16px");

        Div chip = new Div(new Span("Portionen: 4"));
        chip.getStyle().set("display","inline-block");
        chip.getStyle().set("padding","6px 10px");
        chip.getStyle().set("border-radius","12px");
        chip.getStyle().set("background","#efefef");
        chip.getStyle().set("margin","6px 0 10px 0");
        content.add(chip);

        UnorderedList ul = new UnorderedList();
        ul.getStyle().set("list-style","none");
        ul.getStyle().set("padding","0");
        ul.getStyle().set("margin","0");

        for (String line : RECIPES.getOrDefault(slug, RECIPES.get("ratatouille")).ingredients()) {
            ListItem li = new ListItem(line);
            li.getStyle().set("margin","10px 0");
            li.getStyle().set("font-size","18px");
            li.getStyle().set("font-weight","600");
            ul.add(li);
        }
        content.add(ul);
    }

    private void showSteps() {
        content.removeAll();
        content.getStyle().set("padding","12px 16px");

        OrderedList ol = new OrderedList();
        ol.getStyle().set("margin","0");
        ol.getStyle().set("padding-left","20px");

        for (String step : RECIPES.getOrDefault(slug, RECIPES.get("ratatouille")).steps()) {
            ListItem li = new ListItem();
            Paragraph p = new Paragraph(step);
            p.getStyle().set("white-space","pre-wrap");
            p.getStyle().set("font-size","18px");
            li.add(p);
            ol.add(li);
        }
        content.add(ol);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (parameter != null && !parameter.isBlank()) {
            this.slug = parameter;
        }
        RecipeData data = RECIPES.getOrDefault(slug, RECIPES.get("ratatouille"));
        this.title = data.title();
        this.timeText = data.time();
        this.heroImage = data.image();

        // Header ufbaue mit wert
        removeAll();
        add(buildHeader(), content);
        showIngredients();
    }

    // dummy date hardkodiert
    private record RecipeData(String title, String time, String image,
                              List<String> ingredients, List<String> steps) {}

    private static final Map<String, RecipeData> RECIPES = new LinkedHashMap<>();
    static {
        RECIPES.put("ratatouille", new RecipeData(
            "Ratatouille",
            "20 Minuten",
            "/images/ratatouille1.png",
            List.of(
                "1 Aubergine",
                "2 Zucchini",
                "2 Paprika (rot/gelb)",
                "5–6 reife Tomaten oder 1× 400 g Dose stückige Tomaten",
                "1 große Zwiebel",
                "3–4 Knoblauchzehen",
                "Olivenöl",
                "Salz & Pfeffer"
            ),
            List.of(
                "1. Gemüse vorbereiten:\nAubergine und Zucchini in 1–2 cm Würfel, Paprika in Streifen, Tomaten würfeln, Zwiebel fein, Knoblauch hacken.\nOptional: Aubergine salzen (10 min ziehen lassen, abtupfen) → weniger Bitterkeit.",
                "2. Zwiebel & Knoblauch anschwitzen:\nIn 2–3 EL Olivenöl bei mittlerer Hitze ca. 2–3 Minuten.",
                "3. Restliches Gemüse dazugeben:\nErst Aubergine, dann Zucchini & Paprika 5–7 Minuten braten.",
                "4. Tomaten einrühren, würzen & 10–15 Minuten sanft köcheln.\nMit Salz, Pfeffer, optional Kräutern abschmecken."
            )
        ));
    }
}
