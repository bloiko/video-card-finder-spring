package com.example.services.parser;

import com.example.entities.VideoCard;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VideoCardParser {
    public static final int NUM_OF_PAGES = 18;
    private static final BigDecimal DEFAULT = new BigDecimal(200);
    public static final int NUM_OF_VIDEOCARDS = 10;
    @Autowired
    private CardHashRateParser cardHashRateParser;

    public List<VideoCard> getVideoCards() {
        List<String> hrefs = getVideoCardHrefs();
        List<VideoCard> videoCards = new ArrayList<>();
        for (String href : hrefs) {
            try {
                VideoCard videoCard = new VideoCard();
                videoCard.setHref("https://hotline.ua" + href);
                videoCard.setName(getNameFromPage("https://hotline.ua" + href + "?tab=about"));
                videoCard.setHashRate(cardHashRateParser.getNetworkHashRate(videoCard.getName(), "https://2cryptocalc.com/most-profitable-gpu"));
                videoCard.setPower(getPowerFromPage("https://hotline.ua" + href + "?tab=about"));
                videoCard.setPrice(getPriceFromPage("https://hotline.ua" + href));
                log.info("Video card --> "+videoCard.getName() + " " + videoCard.getHashRate() + " " + videoCard.getPower() );
                videoCards.add(videoCard);
            } catch (Exception e) {
                log.error("Cannot parse " + href);
            }
            if (videoCards.size() == NUM_OF_VIDEOCARDS) {
                break;
            }
        }
        return videoCards;
    }

    public String getNameFromPage(String url) {
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
        } catch (IOException e) {
            log.error("Cannot get connetion to the " + url, e);
        }
        if (docCustomCon == null) {
            log.error("Cannot find document in the connection " + url);
        }
        Elements elements = docCustomCon.getElementsByTag("td");

        for (Element element : elements) {
            String text = element.text();
            if (text.equals("GPU:")) {
                Element element2 = element.parent().children().last().children().last();
                return element2.text();
            }
        }

        Element element = docCustomCon.getElementsByAttributeValue("data-tracking-id", "product-106").parents().first();
        if (element != null) {
            return element.text();
        } else return null;
    }

    public BigDecimal getPriceFromPage(String url) {
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
        } catch (IOException e) {
            log.error("Cannot get connetion to the " + url, e);
        }
        if (docCustomCon == null) {
            log.error("Cannot find document in the connection " + url);
        }
        Element element = docCustomCon.getElementsByClass("price__value").first();
        if (element != null) {
            return new BigDecimal(element.text().replace(String.valueOf((char) 160), ""));
        } else return new BigDecimal(0);
    }

    public BigDecimal getPowerFromPage(String url) {
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
        } catch (IOException e) {
            log.error("Cannot get connetion to the " + url, e);
        }
        if (docCustomCon == null) {
            log.error("Cannot find document in the connection "+url);
        }
        Elements elements = docCustomCon.getElementsByTag("td");

        for (Element element : elements) {
            String text = element.text();
            if (text.equals("Споживана потужність, Вт:")) {
                Element element2 = element.parent().children().last().children().last();
                try {
                    return new BigDecimal(element2.text());
                } catch (Exception e) {
                    return DEFAULT;
                }
            }
        }

        return DEFAULT;
    }

    private List<String> getVideoCardHrefs() {
        List<String> hrefs = new ArrayList<>();
        for (int i = 0; i < NUM_OF_PAGES; i++) {
            String url;
            if (i == 0) {
                url = "https://hotline.ua/computer/videokarty/";
            } else {
                url = "https://hotline.ua/computer/videokarty/?p=" + i;
            }
            hrefs.addAll(getHrefsFromOnePage(url));
        }
        return hrefs;
    }

    private List<String> getHrefsFromOnePage(String url) {
        List<String> hrefs = new ArrayList<>();
        Document docCustomCon = null;
        try {
            Connection connection = Jsoup.connect(url);

            docCustomCon = connection.get();
        } catch (IOException e) {
            log.error("Cannot get connetion to the " + url, e);
        }
        if (docCustomCon == null) {
            log.error("Cannot find document in the connection "+url);
        }
        Elements sections = docCustomCon.getElementsByAttributeValue("data-tracking-id", "catalog-10");
        sections.forEach(el -> hrefs.add(el.attr("href")));
        return hrefs;
    }


}
