package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.dto.StatusFormDto;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.StoreItem;
import com.example.auctionshop.repository.StoreItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreItemService {

    @Autowired
    private StoreItemRepository storeItemRepository;


    public StoreItem findById(Long id) {

        return storeItemRepository.findByItem_Id(id);
    }

    public void UpdateStock(StoreItem item , Long stock)
    {
        item.setCount(item.getCount() - stock);

        if(item.getCount() <= 0)
        {
            item.setSellStatus(ItemSellStatus.Complete);
        }

        storeItemRepository.save(item);
    }

    public List<StoreItem> findByStatusWithStatItemAnd(StatusFormDto formDto)
    {
        List<StoreItem> items = storeItemRepository.findStoreItemsByStatWithAnd(
                formDto.getInt_value() , formDto.getDex_value() , formDto.getLuck_value()
                ,formDto.getStr_value(),formDto.getHp_value() , ItemSellStatus.Sell);

        return items;
    }

    public List<StoreItem> findByStatusWithStatItemOR(StatusFormDto formDto)
    {
        List<StoreItem> items = storeItemRepository.findStoreItemsByStatWithOr(
                formDto.getInt_value() , formDto.getDex_value() , formDto.getLuck_value()
                ,formDto.getStr_value(),formDto.getHp_value() ,ItemSellStatus.Sell);

        return items;
    }

    public Page<StoreItem> getSellItems(PageRequest pageRequest , List<StoreItem> items) {
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), items.size());
        List<StoreItem> pageContent = items.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, items.size());
    }




}
