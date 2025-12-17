package ai.novasuite.demo.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@PageTitle("Foody")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private LocalDate day = LocalDate.now();
    private final Text dayLabel = new Text("");

    public HomeView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.STRETCH);
        add(buildHeader(), buildCards());
        updateDate();
    }

    private VerticalLayout buildHeader() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setPadding(true);
        wrapper.setSpacing(false);
        wrapper.setAlignItems(Alignment.STRETCH);

        TextField search = new TextField();
        search.setPlaceholder("Search");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.setWidthFull();
        search.getStyle().set("border-radius", "14px");
        search.getElement().setAttribute("aria-label", "Search");

        HorizontalLayout toggles = new HorizontalLayout();
        toggles.setWidthFull();
        toggles.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        toggles.setAlignItems(Alignment.CENTER);
        toggles.getStyle().set("gap", "10px");

        Button thisWeek = new Button("Diese Woche");
        Button nextWeek = new Button("Nächste Woche");
        for (Button b : new Button[]{thisWeek, nextWeek}) {
            b.getStyle().set("border-radius", "12px");
            b.getStyle().set("background", "#f1f1f1");
        }

        thisWeek.addClickListener(e -> {
            day = LocalDate.now();
            updateDate();
        });
        nextWeek.addClickListener(e -> {
            day = LocalDate.now().plusWeeks(1);
            updateDate();
        });

        toggles.add(thisWeek, nextWeek);

        Button prev = new Button(new Icon(VaadinIcon.ANGLE_LEFT));
        Button next = new Button(new Icon(VaadinIcon.ANGLE_RIGHT));
        prev.addClickListener(e -> {
            day = day.minusDays(1);
            updateDate();
        });
        next.addClickListener(e -> {
            day = day.plusDays(1);
            updateDate();
        });

        Div pill = new Div(dayLabel);
        pill.getStyle().set("padding", "8px 16px");
        pill.getStyle().set("border-radius", "999px");
        pill.getStyle().set("background", "#efefef");
        pill.getStyle().set("font-weight", "600");

        HorizontalLayout dateRow = new HorizontalLayout(prev, pill, next);
        dateRow.setWidthFull();
        dateRow.setAlignItems(Alignment.CENTER);
        dateRow.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        wrapper.add(search, toggles, dateRow);
        return wrapper;
    }

    private VerticalLayout buildCards() {
        VerticalLayout list = new VerticalLayout();
        list.setPadding(true);
        list.setSpacing(true);
        list.setAlignItems(Alignment.STRETCH);

        // bitte mach die bilder noch in resources !!!!!!!!!!!
        list.add(new RecipeCard("Ratatouille", "/images/ratatouille1.png", "ratatouille"));
        list.add(new RecipeCard("Ratatouille", "/images/ratatouille2.png", "ratatouille"));

        return list;
    }

    private void updateDate() {
        dayLabel.setText(day.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
