package com.alanyau.aia_coding_assignment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "title")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank()
    private String title;
    @NotBlank()
    private String author;
    @NotNull()
    private Boolean published;

    public Book(Boolean published, String author, String title) {
        this.published = published;
        this.author = author;
        this.title = title;
    }
}