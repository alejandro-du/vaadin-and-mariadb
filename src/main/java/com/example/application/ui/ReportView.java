package com.example.application.ui;

import com.example.application.backend.BookRepository;
import com.example.application.backend.BookService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("report")
public class ReportView extends VerticalLayout {

    public ReportView(BookService service) {
        var grid = new Grid<>(BookRepository.TopAuthor.class);
        grid.setSizeFull();
        grid.setItems(service.findTopAuthors(12));

        add(grid);
        setSizeFull();
    }
}
