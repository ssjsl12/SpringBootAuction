package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.dto.StatusFormDto;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemImg;
import com.example.auctionshop.entity.ItemStat;
import com.example.auctionshop.entity.StoreItem;
import com.example.auctionshop.repository.ItemImgRepository;
import com.example.auctionshop.repository.ItemRepository;
import com.example.auctionshop.repository.StoreItemRepository;
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
    @Autowired
    private ItemImgRepository itemImgRepository;

    @Autowired
    private StoreItemRepository storeItemRepository;


    public Item findById(Long id) {

        return itemRepository.findItemById(id);
    }

    public void UpdateStock(Item item , Long stock)
    {
        item.setStock_number(item.getStock_number() - stock);

        if(item.getStock_number() <= 0)
        {
            item.setSellStatus(ItemSellStatus.Complete);
        }

        itemRepository.save(item);
    }

    public List<Item> findBySellItems(ItemSellStatus status)
    {
        List<Item> items = itemRepository.findItemsBySellStatus(status);

        return items;
    }

    public List<Item> findByStatusWithStatItemAnd(StatusFormDto formDto)
    {
        List<Item> items = itemRepository.findItemsByStatWithAnd(
                formDto.getInt_value() , formDto.getDex_value() , formDto.getLuck_value()
        ,formDto.getStr_value(),formDto.getHp_value() , ItemSellStatus.Sell);

        return items;
    }

    public List<Item> findByStatusWithStatItemOR(StatusFormDto formDto)
    {
        List<Item> items = itemRepository.findItemsByStatWithOr(
                formDto.getInt_value() , formDto.getDex_value() , formDto.getLuck_value()
                ,formDto.getStr_value(),formDto.getHp_value() ,ItemSellStatus.Sell);

        return items;
    }

    public Page<Item> getSellItems(PageRequest pageRequest , List<Item> items) {
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), items.size());
        List<Item> pageContent = items.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, items.size());
    }

    public void changeItemStatus(Long itemId , ItemSellStatus status)
    {
        Item item = itemRepository.findItemById(itemId);
        StoreItem sItem = storeItemRepository.findByItem_Id(itemId);

        item.setSellStatus(status);
        sItem.setSellStatus(status);

        itemRepository.save(item);
        storeItemRepository.save(sItem);

    }


}
