package com.example.repositories;


import com.example.entities.VideoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VideoCardRepository extends JpaRepository<VideoCard, Long> {

    @Query("select v from VideoCard v order by v.dailyProfit DESC")
    List<VideoCard> getAllOrderByDailyProfit();
}
