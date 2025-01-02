package com.example.auctionshop.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "itemstat")
@Getter
@Setter
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

    @Override
    public String toString() {
        return "ItemStat(id=" + id + ", intel=" + intel + ", luck=" + luck +
                ", dex=" + dex + ", str=" + str + ", hp=" + hp + ", mp=" + mp + ")";
    }
}
