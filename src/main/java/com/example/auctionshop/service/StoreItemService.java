package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.dto.StatusFormDto;
import com.example.auctionshop.entity.CompleteItem;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.StoreItem;
import com.example.auctionshop.entity.WishItem;
import com.example.auctionshop.repository.CompleteItemRepository;
import com.example.auctionshop.repository.ItemRepository;
import com.example.auctionshop.repository.StoreItemRepository;
import com.example.auctionshop.repository.WishItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class StoreItemService {

    @Autowired
    private StoreItemRepository storeItemRepository;

    @Autowired
    private   ItemRepository itemRepository;

    @Autowired
    private WishItemRepository wishItemRepository;

    @Autowired
    private CompleteItemRepository completeItemRepository;

    public StoreItem findByItemId(Long id) {

        return storeItemRepository.findStoreItemById(id);
    }

    public void cancleStoreItem(Long id) {

        StoreItem item =  storeItemRepository.findStoreItemById(id);
        WishItem wItem = wishItemRepository.findWishItemByStoreitem(item);
        item.getItem().setStock_number(item.getItem().getStock_number() + item.getCount());

        if(wItem != null)
        {
            wishItemRepository.delete(wItem);
        }

        storeItemRepository.save(item);

        storeItemRepository.delete(item);
    }

    public void UpdateStock(StoreItem sitem , Long stock)
    {
        sitem.setCount(sitem.getCount() - stock);

        Item item = itemRepository.getItemsById(sitem.getItem().getId());

        item.setStock_number(sitem.getCount());

        if(sitem.getCount() <= 0)
        {
            sitem.setSellStatus(ItemSellStatus.Complete);
            item.setSellStatus(ItemSellStatus.Complete);
        }

        itemRepository.save(item);
        storeItemRepository.save(sitem);
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

    public void AddStoreItem(StoreItem storeItem) {

       Item item = itemRepository.findItemById(storeItem.getItem().getId());

       item.setStock_number(item.getStock_number() - storeItem.getCount());


       storeItemRepository.save(storeItem);
    }


}
