package com.example.services.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
@Service
public class NetworkHashRateParser {

    public  BigDecimal getHashRate() {
        return getNetworkHashRate("https://ycharts.com/indicators/ethereum_network_hash_rate");

    }
    public  BigDecimal getNetworkHashRate(String url) {
        Document docCustomCon = null;
        try {
            Connection connection = Jsoup.connect(url);
            connection.userAgent("Mozilla");
            connection.timeout(10000);
            connection.cookie("cookiename", "val234");
            connection.cookie("cookiename", "val234");
            connection.referrer("http://google.com");
            connection.header("headersecurity", "xyz123");
            if (connection!=null) {
                docCustomCon = connection.get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (docCustomCon == null) {
            System.out.println("Cannot find document in the connection "+url);
            new NoSuchElementException();
        }
        Elements elements = docCustomCon.getElementsByClass("col-6");
        for (Element element : elements) {
            String text = element.text();
            if (text.equals("Last Value")) {
                Element element2 = element.parent().children().last();
                BigDecimal bigDecimal = new BigDecimal(element2.text());
                return new BigDecimal(element2.text());
            }
        }

        return new BigDecimal(0);
    }

}
