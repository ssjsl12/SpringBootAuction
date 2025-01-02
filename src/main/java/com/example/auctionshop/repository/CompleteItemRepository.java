package com.example.auctionshop.repository;

import com.example.auctionshop.entity.CompleteItem;
import com.example.auctionshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompleteItemRepository extends JpaRepository<CompleteItem, Long> {

    @Query("SELECT i FROM CompleteItem i JOIN i.itemStat s " +
            "WHERE (s.intel >= :intel AND s.dex >= :dex AND s.luck >= :luck AND s.str >= :str AND s.hp >= :hp)")
    List<CompleteItem> findItemsByStatWithAnd(@Param("intel") int intel, @Param("dex") int dex, @Param("luck") int luck, @Param("str") int str, @Param("hp") int hp);

    @Query("SELECT i FROM CompleteItem i JOIN i.itemStat s " +
            "WHERE (s.intel > :intel OR s.dex > :dex OR s.luck > :luck OR s.str > :str OR s.hp > :hp)")
    List<CompleteItem> findItemsByStatWithOr(@Param("intel") int intel, @Param("dex") int dex, @Param("luck") int luck, @Param("str") int str, @Param("hp") int hp);


}
