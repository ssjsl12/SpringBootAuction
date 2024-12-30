package com.example.auctionshop.dto;

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

}
