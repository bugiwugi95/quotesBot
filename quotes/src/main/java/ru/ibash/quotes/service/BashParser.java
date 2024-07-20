package ru.ibash.quotes.service;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class BashParser {
    public Map<Integer, String> getPage(int page) {
        Map<Integer, String> quotes = new HashMap<>();
        try {
            Document doc = Jsoup.connect("http://www.ibash.org.ru/?page" + page).get();
            Elements newsHeadlines = doc.select(".quote");

            for (Element quoteElement :
                    newsHeadlines) {
                int id = Integer.parseInt(quoteElement.select("b").first().text().substring(1));
                String text = quoteElement.select(".quotbody").first().text();
                String tag = StringEscapeUtils.unescapeHtml4(text);
                quotes.put(id, tag);

            }
        } catch (IOException ignore) {

        }

        return quotes;
    }

    public Map.Entry<Integer, String> getById(int id)  {
        try {
            Document doc = Jsoup.connect("http://www.ibash.org.ru/quote.php?id=" + id).get();
            Element newsHeadlines = doc.select(".quote").first();
            String realId = newsHeadlines.select("b").first().text();
            if (realId.equals("#???")) return null;
            String text = newsHeadlines.select(".quotbody").first().text();

            return new AbstractMap.SimpleEntry<>(id, text);
        } catch (IOException ignore) {

        }

        return null;
    }

    public Map.Entry<Integer, String> getRandom()  {
        try {
            Document doc = Jsoup.connect("http://www.ibash.org.ru/random.php").get();
            Element newsHeadlines = doc.select(".quote").first();
            String realId = newsHeadlines.select("b").first().text();
            if (realId.equals("#???")) return null;
            String text = newsHeadlines.select(".quotbody").first().text();

            return new AbstractMap.SimpleEntry<>(Integer.parseInt(realId.substring(1)), text);
        } catch (IOException ignore) {

        }

        return null;
    }


}
