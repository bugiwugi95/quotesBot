package ru.ibash.quotes.models;

import jakarta.persistence.*;

@Entity
@Table(name = "\"Chats\"")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "Chats_id_gen", sequenceName = "Chats_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"chatId\"", nullable = false)
    private Long chatId;

    @Column(name = "\"lastId\"", nullable = false)
    private Integer lastId;

    public Integer getLastId() {
        return lastId;
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

}