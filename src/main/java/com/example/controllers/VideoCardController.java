package com.example.controllers;

import com.example.entities.VideoCard;
import com.example.services.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoCardController {
    @Autowired
    private VideoCardService videoCardService;

    @GetMapping("/rating")
    public List<VideoCard> getAllVideoCards() {
        return videoCardService.getAllOrderByDailyProfit();
    }

}
