package com.example.auctionshop.dto;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteItemDto {

    int itemCount;
    int itemPrice;
    int status;
    Long itemId;



}
