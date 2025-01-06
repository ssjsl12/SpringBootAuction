package com.example.auctionshop.controller;

import com.example.auctionshop.dto.WishItemDto;
import com.example.auctionshop.entity.*;
import com.example.auctionshop.service.ItemService;
import com.example.auctionshop.service.MemberService;
import com.example.auctionshop.service.StoreItemService;
import com.example.auctionshop.service.WIshItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@Log4j2
public class CartController
{

    private MemberService memberService;
    private WIshItemService wishItemService;
    private ItemService itemService;
    private StoreItemService storeItemService;

    @GetMapping("/wish/{page}")
    public String wishItem(@PathVariable int page, Model model , Principal principal)
    {
        String email = principal.getName();

        Member user = memberService.findByEmail(email);

        List<WishItem> items = wishItemService.getWishItems(user);

        PageRequest pageRequest = PageRequest.of(page, 12);


        Page<WishItem> sellItemsPage = wishItemService.getWishItems(pageRequest, items);

        model.addAttribute("sellItems", sellItemsPage);
        model.addAttribute("currentPage", sellItemsPage.getNumber());
        model.addAttribute("totalPages", sellItemsPage.getTotalPages());


        if(!email.isEmpty())
        {
            model.addAttribute("user", user.getName());
            model.addAttribute("cash",user.getMeso());
        }

        return "cart/wish";
    }

    @PostMapping("/wish")
    public @ResponseBody ResponseEntity WishPost(@RequestBody @Valid WishItemDto wishItemDto , Principal principal)
    {
        String email = principal.getName();
        Member user = memberService.findByEmail(email);
        List<WishItem> items = wishItemService.getWishItems(user);
        StoreItem item = storeItemService.findByItemId(wishItemDto.getId());

        log.info(wishItemDto.getId());

        log.info(item);

        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getStoreitem().getId() == item.getId())
            {
                return new ResponseEntity<String>("이미 찜한 상품입니다",HttpStatus.BAD_REQUEST);
            }
        }

        wishItemService.insertWishItem(item, user , wishItemDto.getPrice());

        return new ResponseEntity<Long>(wishItemDto.getId(), HttpStatus.OK);
    }



}
