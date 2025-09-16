package it.nepsther.library_system.service;

import it.nepsther.library_system.dto.BookRequestDto;
import it.nepsther.library_system.dto.BookResponseDto;
import it.nepsther.library_system.entity.BookEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBookService {

    BookResponseDto  findBookById(Long id);
    BookResponseDto saveBook(BookRequestDto bookDto);
    void deleteBook(Long id);
    List<BookResponseDto> findAllBooks();
    Page<BookResponseDto> searchBookParms(String title, String author, String isbn, int page, int size);

    Page<BookResponseDto> listBooks(int page, int size);
    List<String> listAuthors();
    Page<BookResponseDto> listBooksByAuthor(String author, int page, int size);
}
