package com.example.services.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
@Service
@Slf4j
public class NetworkHashRateParser {

    public static final String NETWORK_HASH_RATE_URL = "https://ycharts.com/indicators/ethereum_network_hash_rate";

    public  BigDecimal getHashRate() {
        return getNetworkHashRate(NETWORK_HASH_RATE_URL);

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
            docCustomCon = connection.get();
        } catch (IOException e) {
            log.error("Cannot get connetion to the "+url,e);
        }
        if (docCustomCon == null) {
            log.error("Cannot find document in the connection "+url);
        }else {
            Elements elements = docCustomCon.getElementsByClass("col-6");
            for (Element element : elements) {
                String text = element.text();
                if (text.equals("Last Value")) {
                    Element element2 = element.parent().children().last();
                    return new BigDecimal(element2.text());
                }
            }
        }
        return new BigDecimal(0);
    }

}
