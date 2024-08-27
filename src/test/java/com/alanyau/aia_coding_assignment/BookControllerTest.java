package com.alanyau.aia_coding_assignment;

import com.alanyau.aia_coding_assignment.dto.request.BookRequestDTO;
import com.alanyau.aia_coding_assignment.dto.response.ApiResponseDTO;
import com.alanyau.aia_coding_assignment.exception.BookAlreadyExistsException;
import com.alanyau.aia_coding_assignment.exception.BookNotFoundException;
import com.alanyau.aia_coding_assignment.exception.UnknownException;
import com.alanyau.aia_coding_assignment.model.Book;
import com.alanyau.aia_coding_assignment.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    public static final String UNKNOWN_ERROR_MESSAGE = "Unknown error occurred";

    @Test
    public void testCreateBook_Success() throws Exception {
        BookRequestDTO newBookData = new BookRequestDTO("BookTitle1", "Author", true);
        ApiResponseDTO<String> response = new ApiResponseDTO<>(HttpStatus.CREATED.value(), "Book created successfully");

        Mockito.when(bookService.createBook(newBookData)).thenReturn(
                new ResponseEntity<>(response, HttpStatus.CREATED));

        mockMvc.perform(post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBookData)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response").value("Book created successfully"));
    }

    @Test
    public void testCreateBook_AlreadyExists() throws Exception {
        BookRequestDTO newBookData = new BookRequestDTO("BookTitle", "Author2", true);
        String errorMessage = "Book already exists";

        Mockito.when(bookService.createBook(newBookData)).thenThrow(new BookAlreadyExistsException(errorMessage));

        mockMvc.perform(post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBookData)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.response").value(errorMessage));
    }

    @Test
    public void testCreateBook_InvalidInput() throws Exception {
        BookRequestDTO invalidBookData = new BookRequestDTO("BookTitle!!!", "Author???", null);

        mockMvc.perform(post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response").value(
                        "[Author must be alphanumeric only, Published is required, Title must be alphanumeric only]"));
    }

    @Test
    public void testCreateBook_UnknownException() throws Exception {
        BookRequestDTO newBookData = new BookRequestDTO("BookTitle", "Author2", true);

        Mockito.when(bookService.createBook(newBookData)).thenThrow(new UnknownException(UNKNOWN_ERROR_MESSAGE));

        mockMvc.perform(post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBookData)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.response").value(UNKNOWN_ERROR_MESSAGE));
    }

    @Test
    public void testGetBooks_ByNoAuthorAndNoPublished() throws Exception {
        List<Book> books = Arrays.asList(new Book(1L, "BookTitle", "Author", true));
        ApiResponseDTO<List<Book>> response = new ApiResponseDTO<>(HttpStatus.OK.value(), books);

        Mockito.when(bookService.getBooks(null, null)).thenReturn(
                new ResponseEntity<>(response, HttpStatus.OK));

        mockMvc.perform(get("/book/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].author").value("Author"))
                .andExpect(jsonPath("$.response[0].title").value("BookTitle"))
                .andExpect(jsonPath("$.response[0].published").value(true));
    }

    @Test
    public void testGetBooks_ByAuthorAndNoPublished() throws Exception {
        List<Book> books = Arrays.asList(new Book(1L, "BookTitle", "Author", true));
        ApiResponseDTO<List<Book>> response = new ApiResponseDTO<>(HttpStatus.OK.value(), books);

        Mockito.when(bookService.getBooks("Author", null)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/book/all")
                        .param("author", "Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].author").value("Author"))
                .andExpect(jsonPath("$.response[0].title").value("BookTitle"))
                .andExpect(jsonPath("$.response[0].published").value(true));
    }

    @Test
    public void testGetBooks_ByAuthorAndPublished() throws Exception {
        List<Book> books = Arrays.asList(new Book(1L, "BookTitle", "Author", true));
        ApiResponseDTO<List<Book>> response = new ApiResponseDTO<>(HttpStatus.OK.value(), books);

        Mockito.when(bookService.getBooks("Author", true)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/book/all")
                        .param("author", "Author")
                        .param("published", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].author").value("Author"))
                .andExpect(jsonPath("$.response[0].title").value("BookTitle"))
                .andExpect(jsonPath("$.response[0].published").value(true));
    }

    @Test
    public void testGetBooks_ByNoAuthorAndPublished() throws Exception {
        List<Book> books = Arrays.asList(new Book(1L, "BookTitle", "Author", true));
        ApiResponseDTO<List<Book>> response = new ApiResponseDTO<>(HttpStatus.OK.value(), books);

        Mockito.when(bookService.getBooks(null, true)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/book/all")
                        .param("published", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].author").value("Author"))
                .andExpect(jsonPath("$.response[0].title").value("BookTitle"))
                .andExpect(jsonPath("$.response[0].published").value(true));
    }

    @Test
    public void testGetBooks_UnknownException() throws Exception {
        Mockito.when(bookService.getBooks(null, null)).thenThrow(new UnknownException(UNKNOWN_ERROR_MESSAGE));

        mockMvc.perform(get("/book/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.response").value(UNKNOWN_ERROR_MESSAGE));
    }

    @Test
    public void testDeleteBook_Success() throws Exception {
        ApiResponseDTO<String> response = new ApiResponseDTO<>(HttpStatus.OK.value(), "Book deleted successfully");

        Mockito.when(bookService.deleteBook(1)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(delete("/book/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Book deleted successfully"));
    }

    @Test
    public void testDeleteBook_BookNotFoundException() throws Exception {
        Mockito.when(bookService.deleteBook(1)).thenThrow(new BookNotFoundException("Book not found"));

        mockMvc.perform(delete("/book/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.response").value("Book not found"));
    }

    @Test
    public void testDeleteBook_UnknownException() throws Exception {
        Mockito.when(bookService.deleteBook(1)).thenThrow(new UnknownException(UNKNOWN_ERROR_MESSAGE));

        mockMvc.perform(delete("/book/delete/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.response").value(UNKNOWN_ERROR_MESSAGE));
    }
}