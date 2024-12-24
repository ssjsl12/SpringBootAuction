package com.example.auctionshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@SpringBootApplication
public class AuctionShopApplication
{

    public static void main(String[] args) {
        SpringApplication.run(AuctionShopApplication.class, args);
    }

    @GetMapping("/")
    public String home(){


        return "Auction Shop Git";
    }


}

