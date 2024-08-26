package com.alanyau.aia_coding_assignment;

import com.alanyau.aia_coding_assignment.model.Book;
import com.alanyau.aia_coding_assignment.repository.BookRepository;
import com.alanyau.aia_coding_assignment.specification.BookSpecifications;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testSaveBook() {
        Book newBook = new Book(true, "Author", "Book1");

        Book savedBook = bookRepository.save(newBook);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Book1");
        assertThat(savedBook.getAuthor()).isEqualTo("Author");
        assertThat(savedBook.getPublished()).isTrue();
    }

    @Test
    void testFindByTitle() {
        Book book1 = new Book(true, "Author", "Book1");
        Book book2 = new Book(false, "Author", "Book2");
        bookRepository.save(book1);
        bookRepository.save(book2);

        Book foundBook = bookRepository.findByTitle("Book1");

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getTitle()).isEqualTo("Book1");
        assertThat(foundBook.getAuthor()).isEqualTo("Author");
        assertThat(foundBook.getPublished()).isTrue();
    }

    @Test
    void testFindById() {
        Book newBook = new Book(true, "Author", "Book1");
        Book savedBook = bookRepository.save(newBook);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId().intValue());

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Book1");
        assertThat(foundBook.get().getAuthor()).isEqualTo("Author");
        assertThat(foundBook.get().getPublished()).isTrue();
    }

    @Test
    void testFindAll() {
        Book book1 = new Book(true, "Author", "Book1");
        Book book2 = new Book(false, "Author", "Book2");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book1", "Book2");
    }

    @Test
    void testFindAllWithSpecification() {
        // Arrange
        Book book1 = new Book(true, "Author", "Book1");
        Book book2 = new Book(false, "Author", "Book2");
        Book book3 = new Book(true, "Author", "Book3");
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        Specification<Book> specification = BookSpecifications.hasAuthor("Author");
        specification = specification.and(BookSpecifications.hasPublished(true));

        List<Book> books = bookRepository.findAll(specification);

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book1", "Book3");
    }
}