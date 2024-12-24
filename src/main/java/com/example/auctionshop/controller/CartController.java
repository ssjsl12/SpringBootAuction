package com.example.auctionshop.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@Log4j2
public class CartController
{

    @GetMapping("/wish")
    public String wishItem(Model model)
    {



        return "cart/wish";
    }


}
