package it.nepsther.library_system.impl;

import it.nepsther.library_system.dto.BookRequestDto;
import it.nepsther.library_system.dto.BookResponseDto;
import it.nepsther.library_system.entity.BookEntity;
import it.nepsther.library_system.mapper.BookMapper;
import it.nepsther.library_system.repository.IBookRepository;
import it.nepsther.library_system.service.IBookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {
    private final IBookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponseDto findBookById(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookResponseDto saveBook(BookRequestDto bookDto) {
        BookEntity book = bookMapper.toEntity(bookDto);
        BookEntity saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDto> findAllBooks() {
        var response = bookRepository.findAll();
        return bookMapper.toDtoList(response);
    }

    @Override
    public Page<BookResponseDto> searchBookParms(String title, String author, String isbn, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<BookEntity> books = bookRepository.searchBooks(title, author, isbn, pageable);
        return bookMapper.toDtoPage(books);
    }

    @Override
    public Page<BookResponseDto> listBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<BookEntity> books = bookRepository.findAll(pageable);
        return bookMapper.toDtoPage(books);
    }

    @Override
    public List<String> listAuthors() {
        return bookRepository.findAllAuthors();
    }

    @Override
    public Page<BookResponseDto> listBooksByAuthor(String author, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<BookEntity> books = bookRepository.findByAuthorIgnoreCase(author, pageable);
        return bookMapper.toDtoPage(books);
    }
}
