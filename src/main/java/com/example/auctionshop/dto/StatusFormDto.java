package com.example.auctionshop.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatusFormDto {

    private String condition;
    private int int_value;
    private int str_value;
    private int dex_value;
    private int luck_value;
    private int hp_value;



}
