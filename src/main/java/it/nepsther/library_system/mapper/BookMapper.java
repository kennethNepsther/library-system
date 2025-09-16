package it.nepsther.library_system.mapper;

import it.nepsther.library_system.dto.BookRequestDto;
import it.nepsther.library_system.dto.BookResponseDto;
import it.nepsther.library_system.entity.BookEntity;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "title", target = "title", qualifiedByName = "cleanPlainText")
    @Mapping(source = "author", target = "author", qualifiedByName = "cleanPlainText")
    @Mapping(source = "isbn", target = "isbn", qualifiedByName = "cleanPlainText")
    @Mapping(source = "description", target = "description", qualifiedByName = "cleanHtmlAllowBasic")
    BookEntity toEntity(BookRequestDto dto);

    BookResponseDto toDto(BookEntity entity);

    List<BookResponseDto> toDtoList(List<BookEntity> entities);

    default Page<BookResponseDto> toDtoPage(Page<BookEntity> entities) {
        return entities.map(this::toDto);
    }

    // --- Métodos auxiliares de sanitização ---
    @Named("cleanPlainText")
    default String cleanPlainText(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, Safelist.none());
    }

    @Named("cleanHtmlAllowBasic")
    default String cleanHtmlAllowBasic(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, Safelist.basic());
    }
}
