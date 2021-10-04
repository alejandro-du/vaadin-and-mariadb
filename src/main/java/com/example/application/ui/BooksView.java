package com.example.application.ui;

import com.example.application.backend.Book;
import com.example.application.backend.BookService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class BooksView extends VerticalLayout {

    public BooksView(BookService service) {
        var grid = new Grid<Book>();
        grid.setSizeFull();
        grid.addComponentColumn(this::getThumbnail);
        grid.addColumn(Book::getTitle).setHeader("Title");
        grid.addColumn(Book::getAuthor).setHeader("Author");
        grid.addColumn(Book::getPublishDate).setHeader("Publish date");
        grid.addColumn(Book::getPages).setHeader("Pages");
        grid.setPageSize(100);

        grid.setItems(query ->
                service.findAll(query.getPage(), query.getPageSize()));

        var filter = new DatePicker("Filter by publish date");
        filter.addValueChangeListener(event ->
                grid.setItems(query ->
                        service.findAll(filter.getValue(), query.getPage(), query.getPageSize())
                )
        );

        add(filter, grid);
        setSizeFull();
    }

    private Image getThumbnail(Book book) {
        var image = new Image(book.getImageData(), book.getTitle() + " cover");
        image.setHeight("70px");
        image.addClickListener(event -> showCover(book));
        return image;
    }

    private void showCover(Book book) {
        var image = new Image(book.getImageData(), "Cover");
        image.setSizeFull();

        var dialog = new Dialog(image);
        dialog.setHeight("90%");
        dialog.open();
    }
}
