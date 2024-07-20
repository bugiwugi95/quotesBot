package ru.ibash.quotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibash.quotes.models.Quote;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote,Integer> {
    //Optional<Quote> findByOriginalEquals(Integer originalId);

    Optional<Quote> findByColumnidEquals(Integer columnid);
}
