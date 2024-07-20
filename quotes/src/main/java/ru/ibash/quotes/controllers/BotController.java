package ru.ibash.quotes.controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibash.quotes.models.Chat;
import ru.ibash.quotes.models.Quote;
import ru.ibash.quotes.repository.ChatRepository;
import ru.ibash.quotes.service.QuotService;

import java.io.IOException;

@Service
public class BotController {
    private final TelegramBot bot;
    @Autowired
    ChatRepository repository;

    @Autowired
    QuotService quotService;

    public BotController() {

// Create your bot passing the token received from @BotFather
        String botToken = "7426375813:AAFWSlwHnrU2vavh__525hCqq-NPNdthgH4";
        bot = new TelegramBot(botToken);
        System.out.println("Bot started");

// Register for updates
        bot.setUpdatesListener(updates -> {
           for (Update update:updates){
               try {
                   handleUpdate(update);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
// Create Exception Handler
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } else {
                // probably network error
                e.printStackTrace();
            }
        });
    }

    private void handleUpdate(Update update) throws IOException {
       String text =  update.message().text();
       long chatId = update.message().chat().id();
       var rawChat = repository.findByChatIdEquals(chatId);
       Chat chat ;
       if (rawChat.isPresent()){
           chat = rawChat.get();
       }else {
           var _chat = new Chat();
           _chat.setChatId(chatId);
           _chat.setLastId(0);
           chat = repository.save(_chat);
       }
       switch (text){
           case "/start":
           case "/next":
               sendNextQuote(chat);
               break;
           case "/prev":
               sendPrevQuote(chat);
               break;
           case "/rand":
               sendRandom(chat);
               break;
       }
    }

    private void sendPrevQuote(Chat chat)  {
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null){
            newId--;
            if (newId < 2){
                newId = 2;
                quote = quotService.getById(newId);
            }

        }
        chat.setLastId(quote.getColumnid());
        repository.save(chat);
        sendText(chat.getChatId(),quote.getText());
    }

    private void sendNextQuote(Chat chat) {
        Quote quote = null;
        int newId = chat.getLastId();
        while (quote == null){
            newId++;
            quote = quotService.getById(newId);
        }
        chat.setLastId(quote.getColumnid());
        repository.save(chat);
        sendText(chat.getChatId(),quote.getText());
    }


    private void sendRandom(Chat chat)  {
        Quote quote = quotService.getRandom();
        sendText(chat.getChatId(),quote.getText());
    }

    private void sendText(long chatId,String text){
        bot.execute(new SendMessage(chatId,text));

    }
}
