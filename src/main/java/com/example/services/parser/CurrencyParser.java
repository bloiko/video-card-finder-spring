package com.example.services.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class CurrencyParser {

    public static final String HTTPS_COINMARKETCAP_COM_CURRENCIES_ETHEREUM = "https://coinmarketcap.com/currencies/ethereum/";

    public BigDecimal getEthereumPrice() {
        return parsePageWithPrice(HTTPS_COINMARKETCAP_COM_CURRENCIES_ETHEREUM);
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
            log.error("Cannot get connetion to the "+url,e);
        }
        if (docCustomCon == null) {
            log.error("Cannot find document in the connection");
        }else {
            Elements elements = docCustomCon.getElementsByClass("priceValue___11gHJ");
            return new BigDecimal(elements.first().text().replace("$", "").replace(",", ""));
        }
        return new BigDecimal(0);
    }
}
