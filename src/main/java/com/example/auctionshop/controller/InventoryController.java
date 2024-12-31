package com.example.auctionshop.controller;

import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemInventory;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.MemberRepository;
import com.example.auctionshop.service.InventoryService;
import com.example.auctionshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Log4j2
public class InventoryController {

    private final InventoryService inventoryService;
    private final MemberService memberService;

    @GetMapping(value = "/sell")
    public String inventoryForm(Model model , Principal principal)
    {
        String email = principal.getName();

        Member user = memberService.findByEmail(email);

        ItemInventory inventory = inventoryService.getInventory(user);
        //유저가 가지고 있는 아이템.
        List<Item> item = inventoryService.getItems(inventory);

        model.addAttribute("items", item);
        
        return "inventory/sell";
    }
/*
    @PostMapping("/sell")
    public @ResponseBody ResponseEntity changeList()
    {

    }*/

    @GetMapping(value = "/complete")
    public String compleForm(Model model , Principal principal)
    {


        return "inventory/complete";
    }


}
