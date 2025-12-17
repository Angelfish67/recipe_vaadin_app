package ai.novasuite.demo.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@PageTitle("Ecommerce")
@Route(value = "shopping", layout = MainLayout.class)
@CssImport("./styles/main.css")
public class ShoppingListView extends VerticalLayout {

    private LocalDate day = LocalDate.now();
    private final Text dayLabel = new Text("");

    public ShoppingListView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.STRETCH);

        add(buildHeader(), buildList());
        updateDate();
    }

    private VerticalLayout buildHeader() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setPadding(true);
        wrapper.setSpacing(false);
        wrapper.setAlignItems(Alignment.STRETCH);

        H2 title = new H2("Einkaufsliste");
        title.getStyle().set("margin", "0 0 8px 0");
        title.getStyle().set("text-align", "center");

        HorizontalLayout toggles = new HorizontalLayout();
        toggles.setWidthFull();
        toggles.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        toggles.getStyle().set("gap", "10px");

        Button thisWeek = new Button("Diese Woche");
        Button lastWeek = new Button("Letzte Woche");
        for (Button b : new Button[]{thisWeek, lastWeek}) {
            b.getStyle().set("border-radius", "12px");
            b.getStyle().set("background", "#f1f1f1");
        }

        Button prev = new Button(new Icon(VaadinIcon.ANGLE_LEFT));
        Button next = new Button(new Icon(VaadinIcon.ANGLE_RIGHT));
        prev.addClickListener(e -> { day = day.minusDays(1); updateDate(); });
        next.addClickListener(e -> { day = day.plusDays(1); updateDate(); });

        Div pill = new Div(dayLabel);
        pill.getStyle().set("padding","8px 16px");
        pill.getStyle().set("border-radius","999px");
        pill.getStyle().set("background","#efefef");
        pill.getStyle().set("font-weight","600");
        pill.getStyle().set("min-width","160px");
        pill.getStyle().set("text-align","center");

        HorizontalLayout dateRow = new HorizontalLayout(prev, pill, next);
        dateRow.setWidthFull();
        dateRow.setAlignItems(Alignment.CENTER);
        dateRow.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        toggles.add(thisWeek, lastWeek);
        wrapper.add(title, toggles, dateRow, new Hr());
        return wrapper;
    }

    private VerticalLayout buildList() {
        VerticalLayout list = new VerticalLayout();
        list.setPadding(true);
        list.setSpacing(false);
        list.setAlignItems(Alignment.STRETCH);

        list.add(sectionHeader("Gemüse"));
        list.add(chip("3 Paprikas"));
        list.add(chip("2 Brokkoli"));
        list.add(chip("5 Zwiebeln"));
        list.add(chip("2 Brokkoli"));

        list.add(new Hr());

        list.add(sectionHeader("Fleisch"));
        list.add(chip("500g Huhn"));
        list.add(chip("200g Rind"));
        list.add(chip("30g Crevetten"));


        Div spacer = new Div();
        spacer.getStyle().set("height", "16px");
        list.add(spacer);

        return list;
    }

    private H2 sectionHeader(String text) {
        H2 h = new H2(text);
        h.getStyle().set("margin","8px 0 8px");
        h.getStyle().set("font-size","26px");
        return h;
    }

    private Div chip(String text) {
        Div chip = new Div();
        chip.setText(text);
        chip.getStyle().set("background", "#e9e9e9");
        chip.getStyle().set("border-radius", "16px");
        chip.getStyle().set("padding", "16px 18px");
        chip.getStyle().set("margin", "10px 8px");
        chip.getStyle().set("font-size", "22px");
        chip.getStyle().set("font-weight", "700");
        return chip;
    }

    private void updateDate() {
        dayLabel.setText(day.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
