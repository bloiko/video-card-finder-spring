package com.example.services.parser;

import com.example.entities.VideoCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainParser {
    @Autowired
    private VideoCardParser videoCardParser;
    @Autowired
    private ProfitCalculator profitCalculator;

    public List<VideoCard> getAllReadyVideoCards() {
        List<VideoCard> videoCards = videoCardParser.getVideoCards();
        System.out.println("Videocard was parsed from hotline. Without hashrates.");
        System.out.println("------------------------");
        videoCards = profitCalculator.getAllProfits(videoCards);
        return videoCards;
    }
}
