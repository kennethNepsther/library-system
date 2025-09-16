package it.nepsther.library_system.repository;

import it.nepsther.library_system.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByIsbn(String isbn);

    @Query("SELECT DISTINCT b.author FROM BookEntity b")
    List<String> findAllAuthors();

    Page<BookEntity> findByAuthorIgnoreCase(String author, Pageable pageable);

    @Query("""
           SELECT b FROM BookEntity b
           WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
           AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))
           AND (:isbn IS NULL OR b.isbn = :isbn)
           """)
    Page<BookEntity> searchBooks(@Param("title") String title,
                                 @Param("author") String author,
                                 @Param("isbn") String isbn,
                                 Pageable pageable);
}
