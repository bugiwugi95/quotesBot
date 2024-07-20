package ru.ibash.quotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibash.quotes.models.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Integer> {
    //Optional<Quote> findByOriginalEquals(Integer originalId);

    Optional<Chat> findByChatIdEquals(Long chatId);
}
