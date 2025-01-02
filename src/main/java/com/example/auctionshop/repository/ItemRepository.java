package com.example.auctionshop.repository;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>
{
        List<Item> findItemsBySellStatus(ItemSellStatus status);

        @Query("SELECT i FROM Item i JOIN i.itemStat s " +
                "WHERE (s.intel >= :intel AND s.dex >= :dex AND s.luck >= :luck AND s.str >= :str AND s.hp >= :hp AND i.sellStatus = :sellStatus)")
        List<Item> findItemsByStatWithAnd(@Param("intel") int intel, @Param("dex") int dex, @Param("luck") int luck, @Param("str") int str, @Param("hp") int hp,
                                             @Param("sellStatus") ItemSellStatus sellStatus);

        @Query("SELECT i FROM Item i JOIN i.itemStat s " +
                "WHERE (s.intel > :intel OR s.dex > :dex OR s.luck > :luck OR s.str > :str OR s.hp > :hp OR i.sellStatus = :sellStatus)")
        List<Item> findItemsByStatWithOr(@Param("intel") int intel, @Param("dex") int dex, @Param("luck") int luck, @Param("str") int str, @Param("hp") int hp,
                                          @Param("sellStatus") ItemSellStatus sellStatus);

        Item findItemById(Long id);

        List<Item> findItemsByInventoryId(Long inventoryId);

        Item getItemsById(Long id);
}
