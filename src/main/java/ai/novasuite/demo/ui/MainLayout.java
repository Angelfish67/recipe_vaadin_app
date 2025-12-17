package ai.novasuite.demo.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

@CssImport(value = "./styles/main.css")
public class MainLayout extends VerticalLayout implements RouterLayout {

    private final Div contentHost = new Div();

    public MainLayout() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.STRETCH);

        contentHost.setId("content-host");
        contentHost.getStyle().set("flex", "1");
        contentHost.getStyle().set("overflow", "auto");

        add(contentHost, buildBottomBar());
    }

    private Div buildBottomBar() {
        Div bar = new Div();
        bar.setId("bottom-bar");
        bar.getStyle().set("position", "sticky");
        bar.getStyle().set("bottom", "0");
        bar.getStyle().set("background", "white");
        bar.getStyle().set("border-top", "1px solid #eee");
        bar.getStyle().set("padding", "8px 8px 20px 8px");

        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.getStyle().set("gap", "6px");

        row.add(
            tab("Plan", VaadinIcon.HOME, ""),                 
            tab("Suchen", VaadinIcon.SEARCH, ""),             
            tab("Einkauf", VaadinIcon.CART, "shopping"),  // nav   
            tab("Kochbuch", VaadinIcon.BELL, ""),             
            tab("Account", VaadinIcon.USER, "")               
        );

        bar.add(row);
        return bar;
    }

    private VerticalLayout tab(String label, VaadinIcon icon, String route) {
        VerticalLayout tab = new VerticalLayout();
        tab.setPadding(false);
        tab.setSpacing(false);
        tab.setAlignItems(Alignment.CENTER);
        tab.getStyle().set("border-radius", "16px");
        tab.getStyle().set("padding", "6px 8px");

        Icon i = icon.create();
        i.setSize("20px");
        Span text = new Span(label);
        text.getStyle().set("font-size", "12px");
        text.getStyle().set("line-height", "12px");
        tab.add(i, text);

        tab.addClickListener(e -> UI.getCurrent().navigate(route == null || route.isBlank() ? "" : route));
        return tab;
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        contentHost.removeAll();
        if (content instanceof Component c) {
            contentHost.add(c);
        } else {
            contentHost.getElement().appendChild(content.getElement());
        }
    }
}
