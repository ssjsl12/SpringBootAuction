package com.example.auctionshop.controller;

import com.example.auctionshop.dto.StatusFormDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
@Log4j2
public class SideController {



    @GetMapping("/searchForm")
    public String searchForm(Model model)
    {

        model.addAttribute("searchFormDto" , new StatusFormDto());

        return "search/searchForm";
    }



}
