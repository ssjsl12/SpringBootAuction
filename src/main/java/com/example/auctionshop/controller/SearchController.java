package com.example.auctionshop.controller;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.dto.MemberFormDto;
import com.example.auctionshop.dto.StatusFormDto;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.repository.ItemRepository;
import com.example.auctionshop.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Console;
import java.util.List;

@Controller
@RequestMapping("/item")
@AllArgsConstructor
@Log4j2
public class SearchController {

    private ItemService itemService;


    @GetMapping("/search")
    public String search(Model model)
    {

        List<Item> sellItems = itemService.findBySellItems(ItemSellStatus.Sell);

        return "item/search";
    }




    @PostMapping("/search")
    public String optionSearch(
            @Valid StatusFormDto statusFormDto
            , BindingResult bindingResult , Model model
    ) {

        if(bindingResult.hasErrors()) {
            log.info("error");
        }

        log.info("statusFormDto:" + statusFormDto.toString());


        // 검색 결과를 처리하고, 예를 들어 다른 페이지로 리디렉션
        return "/item/search"; // 결과 페이지로 리디렉션
    }



    @GetMapping("/price")
    public String price(Model model)
    {
        List<Item> complteItems = itemService.findBySellItems(ItemSellStatus.Complete);

        model.addAttribute("complteItems", complteItems);

        return "item/search";
    }

}
