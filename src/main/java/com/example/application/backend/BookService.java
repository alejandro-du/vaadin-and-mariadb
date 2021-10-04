package com.example.application.backend;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Stream<Book> findAll(int page, int pageSize) {
        return repository.findAll(PageRequest.of(page, pageSize)).stream();
    }

    public Stream<Book> findAll(LocalDate publishDate, int page, int pageSize) {
        return repository.findByPublishDate(publishDate, PageRequest.of(page, pageSize)).stream();
    }

    public List<BookRepository.TopAuthor> findTopAuthors(int bookCount) {
        return repository.findTopAuthors(bookCount);
    }

}
