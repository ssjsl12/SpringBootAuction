package com.example.auctionshop.repository;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreItemRepository  extends JpaRepository<StoreItem, Integer> {

    @Query("SELECT i FROM StoreItem i JOIN i.item.itemStat s " +
            "WHERE (s.intel >= :intel AND s.dex >= :dex AND s.luck >= :luck AND s.str >= :str AND s.hp >= :hp AND i.sellStatus = :sellStatus)")
    List<StoreItem> findStoreItemsByStatWithAnd(@Param("intel") int intel, @Param("dex") int dex, @Param("luck") int luck, @Param("str") int str, @Param("hp") int hp,
                                                @Param("sellStatus") ItemSellStatus sellStatus);

    @Query("SELECT i FROM StoreItem i JOIN i.item.itemStat s " +
            "WHERE (s.intel > :intel OR s.dex > :dex OR s.luck > :luck OR s.str > :str OR s.hp > :hp OR i.sellStatus = :sellStatus)")
    List<StoreItem> findStoreItemsByStatWithOr(@Param("intel") int intel, @Param("dex") int dex, @Param("luck") int luck, @Param("str") int str, @Param("hp") int hp,
                                     @Param("sellStatus") ItemSellStatus sellStatus);

    StoreItem findByItem_Id(Long itemId);
}
