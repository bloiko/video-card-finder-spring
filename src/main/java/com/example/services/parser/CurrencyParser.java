package com.example.services.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class CurrencyParser {
    public BigDecimal getEthereumPrice() {
        return parsePageWithPrice("https://coinmarketcap.com/currencies/ethereum/");
    }

    private BigDecimal parsePageWithPrice(String url) {
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
            e.printStackTrace();
        }
        if (docCustomCon == null) {
            System.out.println("Cannot find document in the connection");
        }
        Elements elements = docCustomCon.getElementsByClass("priceValue___11gHJ");
        BigDecimal bigDecimal = new BigDecimal(elements.first().text().replace("$", "").replace(",", ""));
        return bigDecimal;
    }
}
