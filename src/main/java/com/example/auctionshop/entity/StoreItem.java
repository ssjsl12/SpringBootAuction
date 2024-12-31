package com.example.auctionshop.entity;

import com.example.auctionshop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "storeItem")
@Getter
@Setter
public class StoreItem {

    @Id
    @Column(name = "store_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    int price;

    Long count;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus sellStatus;

}
