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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@Controller
@RequestMapping("/item")
@AllArgsConstructor
@Log4j2
public class SearchController {

    private ItemService itemService;


    @GetMapping("/search/{page}")
    public String search(@PathVariable int page, Model model) {
        // 'ItemSellStatus.Sell' 상태에 해당하는 아이템 목록을 가져옵니다.
        List<Item> sellItems = itemService.findBySellItems(ItemSellStatus.Sell);

        // PageRequest를 사용하여 해당 페이지를 요청 (10개씩 페이지 처리)
        PageRequest pageRequest = PageRequest.of(page, 12);

        // 페이지네이션 처리
        Page<Item> sellItemsPage = itemService.getSellItems(pageRequest, sellItems);

        // 모델에 sellItemsPage (Page 객체)를 추가
        model.addAttribute("sellItems", sellItemsPage);

        // 현재 페이지 번호와 전체 페이지 수를 모델에 추가
        model.addAttribute("currentPage", sellItemsPage.getNumber());
        model.addAttribute("totalPages", sellItemsPage.getTotalPages());

        return "item/search"; // 'item/search.html'로 이동
    }




    @PostMapping("/search/{page}")
    public String optionSearch(@PathVariable int page,
            @Valid StatusFormDto statusFormDto
            , BindingResult bindingResult , Model model
    ) {

        if(bindingResult.hasErrors()) {
            log.info("error");
        }
        List<Item> sellItems = itemService.findBySellItems(ItemSellStatus.Sell);

        // PageRequest를 사용하여 해당 페이지를 요청 (10개씩 페이지 처리)
        PageRequest pageRequest = PageRequest.of(page, 12);

        // 페이지네이션 처리
        Page<Item> sellItemsPage = itemService.getSellItems(pageRequest, sellItems);

        // 모델에 sellItemsPage (Page 객체)를 추가
        model.addAttribute("sellItems", sellItemsPage);

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
