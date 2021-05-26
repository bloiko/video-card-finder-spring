package com.example.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video_card")
public class VideoCard extends Object{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "href")
   private String href;
   @Column(name = "name")
   private String name;
   @Column(name = "price")
   private BigDecimal price;
   @Column(name = "power")
   private BigDecimal power;
   @Column(name = "hashrate")
   private BigDecimal hashRate;
   @Column(name = "daily_profit")
   private BigDecimal dailyProfit;

}
