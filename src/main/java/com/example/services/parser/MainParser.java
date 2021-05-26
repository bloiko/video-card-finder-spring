package com.example.services.parser;

import com.example.entities.VideoCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MainParser {
    @Autowired
    private VideoCardParser videoCardParser;
    @Autowired
    private ProfitCalculator profitCalculator;

    public List<VideoCard> getAllReadyVideoCards() {
        List<VideoCard> videoCards = videoCardParser.getVideoCards();
        log.info("Videocard was parsed from hotline. Without hashrates.");
        videoCards = profitCalculator.getAllProfits(videoCards);
        return videoCards;
    }
}
