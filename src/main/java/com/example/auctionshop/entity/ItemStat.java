package com.example.auctionshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "itemstat")
@Getter
@Setter
@ToString
public class ItemStat
{
    @Id
    @Column(name = "item_stat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //지능
    private Long intel;
    //운
    private Long luck;
    //회피
    private Long dex;
    //힘
    private Long str;
    //체력
    private Long hp;
    //마나
    private Long mp;


}
