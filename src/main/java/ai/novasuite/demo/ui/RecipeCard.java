package ai.novasuite.demo.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


@CssImport(value = "./styles/main.css")
public class RecipeCard extends VerticalLayout {

    private final String slug;

    public RecipeCard(String title, String imageSrc, String slug) {
        this.slug = slug;

        addClassName("recipe-card");
        setPadding(false);
        setSpacing(false);
        setWidthFull();
        getStyle().set("border-radius","20px");
        getStyle().set("background","linear-gradient(180deg, rgba(0,0,0,0.02), rgba(0,0,0,0.00))");
        getStyle().set("box-shadow","0 10px 24px rgba(0,0,0,0.08)");
        getStyle().set("padding","12px");

        Image img = new Image(imageSrc, title);
        img.setWidth("100%");
        img.getStyle().set("border-radius","18px");
        img.getStyle().set("object-fit","cover");
        img.getStyle().set("height","230px");

        Span name = new Span(title);
        name.getStyle().set("font-size","28px");
        name.getStyle().set("font-weight","800");
        name.getStyle().set("margin","10px 0 6px 0");

        Button cook = new Button("Jetzt kochen", e -> UI.getCurrent().navigate("recipe/" + this.slug));
        cook.getStyle().set("border-radius","24px");
        cook.getStyle().set("background","#f2f2f2");
        cook.getStyle().set("box-shadow","0 8px 18px rgba(0,0,0,0.08)");
        cook.getStyle().set("padding","10px 16px");

        Div btnWrap = new Div(cook);
        btnWrap.getStyle().set("display","flex");
        btnWrap.getStyle().set("justify-content","flex-end");
        btnWrap.getStyle().set("margin","10px 0 6px");

        add(img, name, btnWrap);
    }
}
