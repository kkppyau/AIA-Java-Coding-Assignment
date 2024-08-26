package com.alanyau.aia_coding_assignment.controller;

import com.alanyau.aia_coding_assignment.dto.request.BookRequestDTO;
import com.alanyau.aia_coding_assignment.dto.response.ApiResponseDTO;
import com.alanyau.aia_coding_assignment.exception.BookAlreadyExistsException;
import com.alanyau.aia_coding_assignment.exception.BookNotFoundException;
import com.alanyau.aia_coding_assignment.exception.UnknownException;
import com.alanyau.aia_coding_assignment.model.Book;
import com.alanyau.aia_coding_assignment.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    public BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<String>> createBook(@Valid @RequestBody BookRequestDTO newBookData) throws UnknownException, BookAlreadyExistsException {
        return bookService.createBook(newBookData);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<List<Book>>> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Boolean published
    ) throws UnknownException {
        return bookService.getBooks(author, published);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteBook(@PathVariable int id) throws UnknownException, BookNotFoundException {
        return bookService.deleteBook(id);
    }
}
