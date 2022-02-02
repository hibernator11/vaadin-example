package org.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.service.LCResult;
import org.vaadin.example.service.SearchService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="lc", layout = MainLayout.class)
@PageTitle("Library of Congress")
public class LCView extends VerticalLayout{
	
	private static final long serialVersionUID = 1L;

	public LCView(@Autowired SearchService service) {
        // Use TextField for standard text input
        TextField textField = new TextField("Text to search");
        textField.addThemeName("bordered");
        
        Button button = new Button("Search");
        Grid<LCResult> grid = new Grid<>(LCResult.class);
        
        // Button click listeners can be defined as lambda expressions        
        button.addClickListener( (e) -> grid.setItems(service.search(textField.getValue())));
        grid.setItems(service.search(textField.getValue()));
        
        grid.addColumn(new ComponentRenderer<Anchor,LCResult>(item ->  {
            Anchor anchor = new Anchor();
        	anchor.setHref(item.getUrl());
        	anchor.setText(item.getUrl());
        	return anchor;
        })).setHeader("Link");

       
        grid.setWidth("800px");
        
        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");

        add(textField, button, grid);
	}

}


