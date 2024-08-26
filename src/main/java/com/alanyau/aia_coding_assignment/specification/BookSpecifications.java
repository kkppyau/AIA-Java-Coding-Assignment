package com.alanyau.aia_coding_assignment.specification;

import com.alanyau.aia_coding_assignment.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {
    public static Specification<Book> hasAuthor(String author) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author"), author);
    }

    public static Specification<Book> hasPublished(Boolean published) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("published"), published);
    }
}