package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="todolist", layout = MainLayout.class)
@PageTitle("Todo list")
public class TodoListView extends VerticalLayout{
	
	private static final long serialVersionUID = 1L;
	
    public TodoListView() {
		VerticalLayout todosList = new VerticalLayout(); 
		TextField taskField = new TextField(); 
		Button addButton = new Button("Add"); 
		
		addButton.addClickListener(click -> { 
		  Checkbox checkbox = new Checkbox(taskField.getValue());
		  todosList.add(checkbox);
		});
		addButton.addClickShortcut(Key.ENTER);
		
		// Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");
		
		add(new H1("Todo list"),
		  todosList,
		  new HorizontalLayout(
		    taskField,
		    addButton
		  )
		);
    }
}
