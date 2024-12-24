package com.example.auctionshop.repository;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>
{
        List<Item> findItemsBySellStatus(ItemSellStatus status);
}
