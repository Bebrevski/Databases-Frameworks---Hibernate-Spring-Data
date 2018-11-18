package app.repository;

import app.domain.entities.AgeRestriction;
import app.domain.entities.Author;
import app.domain.entities.Book;
import app.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal minPrice, BigDecimal maxPrice);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate dateBefore, LocalDate After);

    @Query("" +
            "SELECT b " +
            "FROM app.domain.entities.Book AS b " +
            "WHERE b.title LIKE :wildCard")
    List<Book> findAllByGivenPattern(@Param(value = "wildCard") String wildcard);

    @Query("" +
            "SELECT b " +
            "FROM app.domain.entities.Book AS b " +
            "WHERE b.author.lastName LIKE :wildcard " +
            "ORDER BY b.author.firstName ASC")
    List<Book> findAllByAuthorLastNameStartsWith(@Param(value = "wildcard") String wildcard);

    @Query("" +
            "SELECT b " +
            "FROM app.domain.entities.Book AS b " +
            "WHERE length(b.title) >= :length ")
    List<Book> findAllBooksWhereTitleLengthIsLongerThan(@Param(value = "length") int length);

    Book findBookByTitle(String inputTitle);
}
