package com.example.auctionshop.service;

import com.example.auctionshop.entity.CompleteItem;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.entity.WishItem;
import com.example.auctionshop.repository.WishItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WIshItemService {

    @Autowired
    private WishItemRepository wishItemRepository;


    public List<WishItem> getWishItems(Member member)
    {
        List<WishItem> items = wishItemRepository.findByMember(member);

       return items;
    }

    public void insertWishItem(Item item, Member member , int price)
    {
        WishItem wishItem = new WishItem();
        wishItem.setMember(member);
        wishItem.setPrice(price);
        wishItem.setItem(item);

        wishItemRepository.save(wishItem);
    }

    public Page<WishItem> getWishItems(PageRequest pageRequest , List<WishItem> items) {
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), items.size());
        List<WishItem> pageContent = items.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, items.size());
    }


}
