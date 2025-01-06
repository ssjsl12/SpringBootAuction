package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.dto.StatusFormDto;
import com.example.auctionshop.entity.*;
import com.example.auctionshop.repository.ItemImgRepository;
import com.example.auctionshop.repository.ItemRepository;
import com.example.auctionshop.repository.StoreItemRepository;
import com.example.auctionshop.repository.WishItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    private  ItemRepository itemRepository;


    public Item findById(Long id) {

        return itemRepository.findItemById(id);
    }

    public void save(Item item)
    {
        itemRepository.save(item);
    }

    public List<Item> findByItemInventory(ItemInventory itemInventory)
    {
        return itemRepository.findItemsByInventoryId(itemInventory.getId());
    }

}
