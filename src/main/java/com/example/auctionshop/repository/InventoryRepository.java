package com.example.auctionshop.repository;

import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<ItemInventory,Long>
{
        public ItemInventory findByMemberId(long memberId);



}
