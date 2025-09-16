package it.nepsther.library_system.dto;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        String description,
        String isbn,
        Integer availableCopies,
        boolean available
) {
}
