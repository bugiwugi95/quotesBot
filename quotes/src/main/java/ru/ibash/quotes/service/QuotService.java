package ru.ibash.quotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ibash.quotes.models.Quote;
import ru.ibash.quotes.repository.QuoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class QuotService {
    @Autowired
    BashParser bashParser;
    @Autowired
    QuoteRepository quoteRepository;

    public List<Quote> getPage(int page) {
        List<Quote> quotes = new ArrayList<>();

            Map<Integer, String> map = bashParser.getPage(page);
            for (var entry : map.entrySet()) {
                var rawQuote = new Quote();
                rawQuote.setColumnid(entry.getKey());
                rawQuote.setText(entry.getValue());
                var exists = quoteRepository.findByColumnidEquals(rawQuote.getColumnid());
                if (exists.isEmpty()) {
                    quotes.add(quoteRepository.save(rawQuote));
                } else {
                    quotes.add(exists.get());
                }


            }


        return quotes;
    }

    public Quote getById(int id) {
        var existingQuote = quoteRepository.findByColumnidEquals(id);
        if (existingQuote.isPresent()) {
            return existingQuote.get();
        }
        var quoteEntry = bashParser.getById(id);
        if (quoteEntry == null) return null;
        var newQuote = new Quote();
        newQuote.setColumnid(quoteEntry.getKey());
        newQuote.setText(quoteEntry.getValue());
        return quoteRepository.save(newQuote);


    }

    public Quote getRandom(){

        var quoteEntry = bashParser.getRandom();
        if (quoteEntry == null) return null;

        var existingQuote = quoteRepository.findByColumnidEquals(quoteEntry.getKey());
        if (existingQuote.isPresent()) {
            return existingQuote.get();
        }
        var newQuote = new Quote();
        newQuote.setColumnid(quoteEntry.getKey());
        newQuote.setText(quoteEntry.getValue());
        return quoteRepository.save(newQuote);


    }
}
