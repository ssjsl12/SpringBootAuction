package com.example.auctionshop.controller;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.dto.MemberFormDto;
import com.example.auctionshop.dto.OrderDto;
import com.example.auctionshop.dto.StatusFormDto;
import com.example.auctionshop.entity.CompleteItem;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.ItemRepository;
import com.example.auctionshop.service.CompleteItemService;
import com.example.auctionshop.service.ItemService;
import com.example.auctionshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Console;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/item")
@AllArgsConstructor
@Log4j2
public class SearchController {

    private ItemService itemService;
    private MemberService memberService;
    private CompleteItemService completeItemService;
    @GetMapping("/search/{page}")
    public String search(@PathVariable int page
            ,HttpSession session
            , Model model ,@Valid StatusFormDto statusFormDto) {
        // 'ItemSellStatus.Sell' 상태에 해당하는 아이템 목록을 가져옵니다.

        if(session.getAttribute("statusFormDto") != null)
            statusFormDto = (StatusFormDto)session.getAttribute("statusFormDto");

        List<Item> sellItems = new ArrayList<>();

        if(statusFormDto.getCondition() == "OR")
        {
            sellItems = itemService.findByStatusWithStatItemOR(statusFormDto);
        }
        else
        {
            sellItems = itemService.findByStatusWithStatItemAnd(statusFormDto);
        }


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

    @GetMapping("/price/{page}")
    public String price(@PathVariable int page ,HttpSession session , @Valid  StatusFormDto statusFormDto, Model model)
    {

        if(session.getAttribute("statusFormDto") != null)
        statusFormDto = (StatusFormDto)session.getAttribute("statusFormDto");

        List<CompleteItem> completeItems = new ArrayList<>();

        if(statusFormDto.getCondition() == "OR")
        {
            completeItems = completeItemService.findByStatusWithStatItemOR(statusFormDto);
        }
        else
        {
            completeItems = completeItemService.findByStatusWithStatItemAnd(statusFormDto);
        }

        PageRequest pageRequest = PageRequest.of(page, 12);

        // 페이지네이션 처리
        Page<CompleteItem> sellItemsPage = completeItemService.getCompleteItems(pageRequest, completeItems);

        // 모델에 sellItemsPage (Page 객체)를 추가
        model.addAttribute("sellItems", sellItemsPage);

        model.addAttribute("currentPage", sellItemsPage.getNumber());
        model.addAttribute("totalPages", sellItemsPage.getTotalPages());

        return "item/price"; // 'item/search.html'로 이동
    }

    @PostMapping("/priceStatus")
    public String priceStatus(@Valid StatusFormDto statusFormDto, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "redirect:/item/price/0";
        }

        // StatusFormDto 값을 세션에 저장
        session.setAttribute("statusFormDto", statusFormDto);

        return "redirect:/item/price/0";  // 리다이렉트하여 페이지 넘기기
    }

    @PostMapping("/searchStatus")
    public String searchStatus(@Valid StatusFormDto statusFormDto, BindingResult bindingResult, HttpSession session) {

        log.info(statusFormDto.toString());

        if (bindingResult.hasErrors()) {

            log.info("here?");
            return "redirect:/item/search/0";
        }

        // StatusFormDto 값을 세션에 저장
        session.setAttribute("statusFormDto", statusFormDto);

        return "redirect:/item/search/0";  // 리다이렉트하여 페이지 넘기기

    }

    //타이틀을 눌렀을때 status 초기화

    @GetMapping("/search")
    public String titleSearch(HttpSession session, Model model)
    {
        StatusFormDto statusFormDto = new StatusFormDto();

        log.info(statusFormDto.toString());

        session.setAttribute("statusFormDto", statusFormDto);

        return "redirect:/item/search/0";
    }

    @GetMapping("/price")
    public String titlePrice(HttpSession session, Model model)
    {
        StatusFormDto statusFormDto = new StatusFormDto();

        log.info(statusFormDto.toString());

        session.setAttribute("statusFormDto", statusFormDto);

        return "redirect:/item/price/0";
    }

    @PatchMapping("/search")
    public @ResponseBody ResponseEntity buy(@RequestBody @Valid OrderDto orderDto
            , Principal principal)
    {
        String email = principal.getName();

        Member user = memberService.findByEmail(email);

        int itemPrice = (int)(orderDto.getPrice() * orderDto.getStock());


        if(user.getMeso() < itemPrice)
        {
            log.info("here?");

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        memberService.updateMemberMeso(user,(int)itemPrice);

        Item item = itemService.findById(orderDto.getId());

        itemService.UpdateStock(item,orderDto.getStock());

        CompleteItem cItem = new CompleteItem();
        cItem.setItem(item);
        cItem.setCount(orderDto.getStock());
        completeItemService.AddCompleteItem(cItem);

        return new ResponseEntity<Long>(orderDto.getId(), HttpStatus.OK);
    }


}
