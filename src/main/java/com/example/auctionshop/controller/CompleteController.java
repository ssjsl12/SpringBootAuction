package com.example.auctionshop.controller;

import com.example.auctionshop.dto.CompleteItemDto;
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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @GetMapping(value = "/complete/{page}")
    public String compleForm(@PathVariable int page, Model model , Principal principal)
    {
        String email = principal.getName();

        Member user = memberService.findByEmail(email);
        ItemInventory inventory = inventoryService.getInventory(user);

        List<CompleteItem> allcItem = completeItemService.getItems();
        List<CompleteItem> mycItem = new ArrayList<CompleteItem>();

        for(int i =0 ; i < allcItem.size() ; i++)
        {
           Item item = itemRepository.findItemById(allcItem.get(i).getId());

           if(item.getInventory().getId() == inventory.getId() && allcItem.get(i).getStatus() == 0)
           {
               mycItem.add(allcItem.get(i));
           }
        }

        PageRequest pageRequest = PageRequest.of(page, 12);

        Page<CompleteItem> compleItemPage = completeItemService.getCompleteItems(pageRequest, mycItem);

        // 모델에 sellItemsPage (Page 객체)를 추가
        model.addAttribute("completeItem", compleItemPage);

        if(!email.isEmpty())
        {
            model.addAttribute("user", user.getName());
            model.addAttribute("cash",user.getMeso());
        }


        return "inventory/complete";
    }


    @PostMapping("/complete")
    public @ResponseBody ResponseEntity collectItem(
            @RequestBody @Valid CompleteItemDto cDto, Principal principal)
    {
        int itemCount = cDto.getItemCount();
        int itemPrice = cDto.getItemPrice();
        Long itemId = cDto.getItemId();

        String email = principal.getName();
        Member user = memberService.findByEmail(email);

        user.inComeMeso(itemCount * itemPrice);

        memberService.updateMember(user);

        completeItemService.setComplteItemStatus(itemId,1);

        return new ResponseEntity<String>("회수 완료", HttpStatus.OK);
    }



}
