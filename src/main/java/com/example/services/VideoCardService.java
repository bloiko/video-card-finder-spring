package com.example.services;

import com.example.entities.VideoCard;
import com.example.repositories.VideoCardRepository;
import com.example.services.parser.MainParser;
import com.example.services.parser.ProfitCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class VideoCardService {
    @Autowired
    public VideoCardRepository videoCardRepository;

    @Autowired
    private MainParser mainParser;
    @Autowired
    private ProfitCalculator profitCalculator;

    @Transactional
    public List<VideoCard> getAllOrderByDailyProfit() {
        return videoCardRepository.getAllOrderByDailyProfit();
    }

    @Scheduled(fixedDelay = 20 * 60 * 1000)
    @Transactional
    public void getVideoCards() {
        List<VideoCard> cardList = mainParser.getAllReadyVideoCards();
        cardList = profitCalculator.getAllProfits(cardList);
        videoCardRepository.deleteAll();
        videoCardRepository.saveAll(cardList);
    }


}
