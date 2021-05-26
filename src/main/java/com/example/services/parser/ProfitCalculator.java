package com.example.services.parser;

import com.example.entities.VideoCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
@Service
public class ProfitCalculator {
    private static final BigDecimal PRICE_PER_KILOWATT=new BigDecimal("0.061");
    private static final BigDecimal ETHEREUMS_PER_BLOCK=new BigDecimal(20350);
    private  BigDecimal ethereumCurrency;
    @Autowired
    private CurrencyParser currencyParser;
    @Autowired
    private NetworkHashRateParser networkHashRateParser;

    public List<VideoCard> getAllProfits(List<VideoCard> videoCards){
        ethereumCurrency = currencyParser.getEthereumPrice();
        for (VideoCard videoCard: videoCards){
            videoCard.setDailyProfit(getDailyProfit(videoCard));
        }
        return  videoCards;
    }
    public BigDecimal getDailyProfit(VideoCard videoCard){
        BigDecimal ourHashRate = videoCard.getHashRate().divide(new BigDecimal(1_000_000), 10, RoundingMode.HALF_EVEN);
        BigDecimal networkHashRate = networkHashRateParser.getHashRate();
        BigDecimal hashRateShare = ourHashRate.divide(networkHashRate, 10, RoundingMode.HALF_EVEN);
        BigDecimal dailyBlockReward = ETHEREUMS_PER_BLOCK;
        BigDecimal dailyEthereum = dailyBlockReward.multiply(hashRateShare);
        //Costs
        BigDecimal kilowattsPerDay = videoCard.getPower().divide(new BigDecimal(1000), 5, RoundingMode.HALF_EVEN);
        BigDecimal powerPrice = kilowattsPerDay.multiply(PRICE_PER_KILOWATT);
        //Profitability
        BigDecimal dailyRevenue = dailyEthereum.multiply(ethereumCurrency);
        return  dailyRevenue.subtract(powerPrice).round(new MathContext(3));
    }
}
