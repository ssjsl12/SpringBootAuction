package com.example.auctionshop.dto;

import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemImg;
import com.example.auctionshop.entity.ItemStat;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    Long id;
    Long price;
    Long stock;
    String itemImg;
    ItemStat itemStat;
    String name;
    Long itemId;
}
