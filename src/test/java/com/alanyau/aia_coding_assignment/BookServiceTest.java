package com.alanyau.aia_coding_assignment;

import static org.junit.jupiter.api.Assertions.*;

import com.alanyau.aia_coding_assignment.dto.request.BookRequestDTO;
import com.alanyau.aia_coding_assignment.dto.response.ApiResponseDTO;
import com.alanyau.aia_coding_assignment.exception.BookAlreadyExistsException;
import com.alanyau.aia_coding_assignment.exception.BookNotFoundException;
import com.alanyau.aia_coding_assignment.exception.UnknownException;
import com.alanyau.aia_coding_assignment.model.Book;
import com.alanyau.aia_coding_assignment.repository.BookRepository;
import com.alanyau.aia_coding_assignment.service.BookService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testCreateBook_Success() throws Exception {
        BookRequestDTO newBookData = new BookRequestDTO("Book Title", "Author", true);
        Mockito.when(bookRepository.findByTitle("Book Title")).thenReturn(null);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(new Book(true, "Author", "Book Title"));

        ResponseEntity<ApiResponseDTO<String>> response = bookService.createBook(newBookData);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New book has been created", Objects.requireNonNull(response.getBody()).getResponse());
    }

    @Test
    public void testCreateBook_BookAlreadyExists() throws Exception {
        BookRequestDTO newBookData = new BookRequestDTO("Book Title", "Author", true);
        Mockito.when(bookRepository.findByTitle("Book Title")).thenReturn(new Book(true, "Author", "Book Title"));

        assertThrows(BookAlreadyExistsException.class, () -> bookService.createBook(newBookData));
    }

    @Test
    public void testGetBooks_AllParams() throws Exception {
        Mockito.when(bookRepository.findAll(Mockito.any(Specification.class))).thenReturn(Arrays.asList(
                new Book(true, "Author1", "Book1")
        ));
        ResponseEntity<ApiResponseDTO<List<Book>>> response = bookService.getBooks("Author1", true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getResponse().size());
    }

    @Test
    public void testDeleteBook_Success() throws Exception {
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(new Book(true, "Author", "Book Title")));

        ResponseEntity<ApiResponseDTO<String>> response = bookService.deleteBook(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book has been deleted", response.getBody().getResponse());
    }

    @Test
    public void testDeleteBook_BookNotFound() throws Exception {
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1));
    }

    @Test
    public void testCreateBook_UnknownException() throws Exception {
        BookRequestDTO newBookData = new BookRequestDTO("Book Title", "Author", true);
        Mockito.when(bookRepository.findByTitle("Book Title")).thenThrow(new RuntimeException("Unknown exception"));

        assertThrows(UnknownException.class, () -> bookService.createBook(newBookData));
    }

    @Test
    public void testGetBooks_UnknownException() throws Exception {
        Mockito.when(bookRepository.findAll(Mockito.any(Specification.class))).thenThrow(
                new RuntimeException("Unknown exception"));

        assertThrows(UnknownException.class, () -> bookService.getBooks(null, null));
    }

    @Test
    public void testDeleteBook_UnknownException() throws Exception {
        Mockito.when(bookRepository.findById(1)).thenThrow(new RuntimeException("Unknown exception"));

        assertThrows(UnknownException.class, () -> bookService.deleteBook(1));
    }
}