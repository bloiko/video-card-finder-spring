package com.example.services.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
@Service
@Slf4j
public class CardHashRateParser {
    public  BigDecimal getNetworkHashRate(String name,String url) {
        Document docCustomCon = null;
        try {
            Connection connection = Jsoup.connect(url);
            connection.userAgent("Mozilla");
            connection.timeout(5000);
            connection.cookie("cookiename", "val234");
            connection.cookie("cookiename", "val234");
            connection.referrer("http://google.com");
            connection.header("headersecurity", "xyz123");
            docCustomCon = connection.get();
        } catch (Exception e) {
           log.error("Cannot get connetion to the "+url,e);
        }
        if (docCustomCon == null) {
            log.error("Cannot find document in the connection");
        }else {
            Elements elements = docCustomCon.getElementsByClass("crypto-coin__name");
            for (Element element : elements) {
                String text = element.text();
                if (text.toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) {
                    Elements element2 = element.parent().parent().parent().parent().children();
                    for (Element child : element2) {
                        String text1 = child.children().first().children().first().text();
                        if ("Hashrate".equals(text1)) {
                            return new BigDecimal(child.children().last().children().first().children().first().text());
                        }
                    }
                    return new BigDecimal(element2.text());
                }
            }
        }

        return new BigDecimal(0);
    }
}
