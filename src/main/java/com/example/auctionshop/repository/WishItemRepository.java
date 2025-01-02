package com.example.auctionshop.repository;

import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishItemRepository extends JpaRepository<WishItem, Integer> {


    List<WishItem> findByMember(Member member);

    WishItem findByItemId(long itemId);

}
