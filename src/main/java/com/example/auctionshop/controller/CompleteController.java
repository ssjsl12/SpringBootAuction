package com.example.auctionshop.controller;

import com.example.auctionshop.entity.CompleteItem;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemInventory;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.ItemRepository;
import com.example.auctionshop.service.CompleteItemService;
import com.example.auctionshop.service.InventoryService;
import com.example.auctionshop.service.ItemService;
import com.example.auctionshop.service.MemberService;
import groovy.transform.AutoClone;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Log4j2
public class CompleteController {

    @Autowired
    CompleteItemService completeItemService;
    private final MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    private final InventoryService inventoryService;


    @GetMapping(value = "/complete")
    public String compleForm(Model model , Principal principal)
    {
        String email = principal.getName();

        Member user = memberService.findByEmail(email);
        ItemInventory inventory = inventoryService.getInventory(user);

        List<CompleteItem> allcItem = completeItemService.getItems();
        List<CompleteItem> mycItem = new ArrayList<CompleteItem>();

        for(int i =0 ; i < allcItem.size() ; i++)
        {
           Item item = itemRepository.findItemById(allcItem.get(i).getId());

           if(item.getInventory().getId() == inventory.getId())
           {
               mycItem.add(allcItem.get(i));
           }
        }

        model.addAttribute("items", mycItem);

        return "inventory/complete";
    }


}
