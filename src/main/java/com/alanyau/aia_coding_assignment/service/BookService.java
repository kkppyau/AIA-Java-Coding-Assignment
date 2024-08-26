package com.alanyau.aia_coding_assignment.service;

import com.alanyau.aia_coding_assignment.dto.request.BookRequestDTO;
import com.alanyau.aia_coding_assignment.dto.response.ApiResponseDTO;
import com.alanyau.aia_coding_assignment.exception.BookAlreadyExistsException;
import com.alanyau.aia_coding_assignment.exception.BookNotFoundException;
import com.alanyau.aia_coding_assignment.exception.UnknownException;
import com.alanyau.aia_coding_assignment.model.Book;
import com.alanyau.aia_coding_assignment.repository.BookRepository;
import com.alanyau.aia_coding_assignment.specification.BookSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<ApiResponseDTO<String>> createBook(BookRequestDTO newBookData) throws UnknownException, BookAlreadyExistsException {
        try {
            // book already exists
            if (bookRepository.findByTitle(newBookData.getTitle()) != null) {
                throw new BookAlreadyExistsException("Book already exists with title " + newBookData.getTitle());
            }

            Book newBook = new Book(newBookData.getPublished(), newBookData.getAuthor(), newBookData.getTitle());
            bookRepository.save(newBook);

            return new ResponseEntity<>(new ApiResponseDTO<>(HttpStatus.CREATED.value(), "New book has been created"),
                    HttpStatus.CREATED);
        } catch (BookAlreadyExistsException e) {
            throw new BookAlreadyExistsException(e.getMessage());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
    }

    public ResponseEntity<ApiResponseDTO<List<Book>>> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Boolean published
    ) throws UnknownException {
        try {
            Specification<Book> specification = Specification.where(null);

            if (author != null) {
                specification = specification.and(BookSpecifications.hasAuthor(author));
            }

            if (published != null) {
                specification = specification.and(BookSpecifications.hasPublished(published));
            }

            List<Book> books = bookRepository.findAll(specification);

            return new ResponseEntity<>(new ApiResponseDTO<>(HttpStatus.OK.value(), books), HttpStatus.OK);
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
    }

    public ResponseEntity<ApiResponseDTO<String>> deleteBook(int id) throws UnknownException, BookNotFoundException {
        try {
            Optional<Book> book = bookRepository.findById(id);

            // book not exists
            if (book.isEmpty()) {
                throw new BookNotFoundException("Book not found with id " + id);
            }

            // book exists, delete it
            bookRepository.delete(book.get());
            return new ResponseEntity<>(
                    new ApiResponseDTO<>(HttpStatus.OK.value(), "Book has been deleted"),
                    HttpStatus.OK);
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
    }
}
