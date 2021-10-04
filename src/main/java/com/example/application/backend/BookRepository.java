package com.example.application.backend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    interface TopAuthor{
        String getAuthor();
        int getBookCount();
    }

    Page<Book> findByPublishDate(LocalDate publishDate, Pageable pageable);

    @Query(value = "select author, count(id) as bookCount from book_analytics group by author having count(id) > ?1", nativeQuery = true)
    List<TopAuthor> findTopAuthors(int bookCount);

}
