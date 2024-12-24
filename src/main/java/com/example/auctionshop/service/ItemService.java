package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
