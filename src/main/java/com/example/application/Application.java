package com.example.application;

import com.example.application.backend.Book;
import com.example.application.backend.BookRepository;
import com.vaadin.exampledata.ChanceIntegerType;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@Theme(value = "demo")
@PWA(name = "Demo", shortName = "Demo", offlineResources = {"images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@Log4j2
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner createDemoDataIfNeeded(BookRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                log.info("Generating demo data...");
                var generator = new ExampleDataGenerator<>(Book.class, LocalDateTime.now());
                generator.setData(Book::setImageData, DataType.BOOK_IMAGE_URL);
                generator.setData(Book::setTitle, DataType.BOOK_TITLE);
                generator.setData(Book::setAuthor, DataType.FULL_NAME);
                generator.setData(Book::setPublishDate, DataType.DATE_LAST_10_YEARS);
                generator.setData(Book::setPages, new ChanceIntegerType("integer", "{min: 20, max: 1000}"));

                var stopWatch = new StopWatch();
                stopWatch.start();
                List<Book> books = generator.create(100, new Random().nextInt());
                repository.saveAll(books);
                stopWatch.stop();
                log.info("Demo data generated in " + stopWatch.getTime() + "ms.");
            }
        };
    }

}
