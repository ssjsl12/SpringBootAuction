package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private  ItemRepository itemRepository;

    public List<Item> findBySellItems(ItemSellStatus status)
    {
        List<Item> items = itemRepository.findItemsBySellStatus(status);

        return items;
    }

    public Page<Item> getSellItems(PageRequest pageRequest , List<Item> items) {
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), items.size());
        List<Item> pageContent = items.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, items.size());
    }



}
