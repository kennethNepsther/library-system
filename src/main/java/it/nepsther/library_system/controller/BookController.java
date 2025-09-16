package it.nepsther.library_system.controller;

import it.nepsther.library_system.dto.PageResponse;
import it.nepsther.library_system.dto.BookRequestDto;
import it.nepsther.library_system.dto.BookResponseDto;
import it.nepsther.library_system.service.IBookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final IBookService bookService;
    // Buscar livro por ‘ID’
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

        // Criar ou atualizar livro
    @PostMapping
    public ResponseEntity<BookResponseDto> saveBook(@Valid @RequestBody BookRequestDto bookDto) {
        BookResponseDto savedBook = bookService.saveBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // Apagar livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos os livros (sem paginação)
    @GetMapping("/all")
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    // Listar livros com paginação
    @GetMapping("/list")
    public ResponseEntity<PageResponse<BookResponseDto>> listBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(PageResponse.from(bookService.listBooks(page, size)));
    }
    // Buscar livros por filtros (title, author, isbn)
    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookResponseDto>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return ResponseEntity.ok(PageResponse.from(
                bookService.searchBookParms(title, author, isbn, page, size)
        ));

    }

    // Listar autores únicos
    @GetMapping("/authors")
    public ResponseEntity<List<String>> listAuthors() {
        return ResponseEntity.ok(bookService.listAuthors());
    }

    // Listar livros por autor (com paginação)
    @GetMapping("/author/{author}")
    public ResponseEntity<PageResponse<BookResponseDto>> listBooksByAuthor(
            @PathVariable String author,
            @RequestParam(defaultValue = "0")  @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(PageResponse.from(
                bookService.listBooksByAuthor(author, page, size)
        ));
    }

}
