package com.example.auctionshop.service;

import com.example.auctionshop.dto.StatusFormDto;
import com.example.auctionshop.entity.CompleteItem;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.repository.CompleteItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompleteItemService {

    @Autowired
    private  CompleteItemRepository completeItemRepository;

    public void AddCompleteItem(CompleteItem completeItem)
    {
        completeItemRepository.save(completeItem);
    }

    public List<CompleteItem> findByStatusWithStatItemAnd(StatusFormDto formDto)
    {
        List<CompleteItem> items = completeItemRepository.findItemsByStatWithAnd(
                formDto.getInt_value() , formDto.getDex_value() , formDto.getLuck_value()
                ,formDto.getStr_value(),formDto.getHp_value());

        return items;
    }

    public List<CompleteItem> findByStatusWithStatItemOR(StatusFormDto formDto)
    {
        List<CompleteItem> items = completeItemRepository.findItemsByStatWithOr(
                formDto.getInt_value() , formDto.getDex_value() , formDto.getLuck_value()
                ,formDto.getStr_value(),formDto.getHp_value());

        return items;
    }

    public Page<CompleteItem> getCompleteItems(PageRequest pageRequest , List<CompleteItem> items) {
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), items.size());
        List<CompleteItem> pageContent = items.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, items.size());
    }

    public List<CompleteItem> getItems()
    {
        return completeItemRepository.findAll();
    }

}
