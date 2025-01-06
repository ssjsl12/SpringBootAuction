package com.example.auctionshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "completeItem")
@Getter
@Setter
public class CompleteItem {

    @Id
    @Column(name ="item_comple_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemId;

    private String itemImg;

    private Long count;

    private String name;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_stat_id")
    private ItemStat itemStat;

    private int status;

}
