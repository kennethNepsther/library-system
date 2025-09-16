package it.nepsther.library_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotBlank
        String author,
        @NotBlank
        String isbn,
        @NotNull
        @Min(0)
        Integer availableCopies
) {
}
