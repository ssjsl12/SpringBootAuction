package com.example.auctionshop.entity;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.constant.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //아이템 타입
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus sellStatus;

    //개수
    private Long stock_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_inventory_id")
    private ItemInventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_img_id")
    private ItemImg itemImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_stat_id")
    private ItemStat itemStat;


    //비교
    public boolean compareItem(ItemStat itemStat , String name)
    {
        if(this.itemStat == itemStat
        && this.name.equals(name))
        {
            return true;
        }
        else{
            return false;
        }
    }



}
