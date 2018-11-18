package app.repository;

import app.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    //Task 6 Built in Query
    //List<Author> findAllByFirstNameEndsWith(String letter);

    //Task 6 Custom Query
    @Query("" +
            "SELECT a " +
            "FROM app.domain.entities.Author AS a " +
            "WHERE a.firstName LIKE :wildCard")
    List<Author> findAllByFirstNameEndsWithCustomQuery(@Param(value = "wildCard") String wildCard);
}
