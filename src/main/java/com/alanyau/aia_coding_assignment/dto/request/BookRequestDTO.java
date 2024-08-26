package com.alanyau.aia_coding_assignment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    @NotBlank(message = "Title is required")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Title must be alphanumeric only")
    private String title;
    @NotBlank(message = "Author is required")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Author must be alphanumeric only")
    private String author;
    @NotNull(message = "Published is required")
    private Boolean published;
}
