package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemType;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemInventory;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.InventoryRepository;
import com.example.auctionshop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
class InventoryServiceTest
{
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void setInventoryService()
    {
        Member member = memberRepository.findByEmail("admin@test.com");
        ItemInventory inventory = inventoryRepository.findByMemberId(member.getId());

        Item item = new Item();
        item.setInventory(inventory);
        item.setId(1L);
        item.setName("test");
        item.setType(ItemType.Equip);
        item.setStock_number(1L);

        inventory.getItems().add(item);

        for(int i = 0 ; i < inventory.getItems().size(); i++)
        {
            log.info("------------------------");
            log.info(inventory.getItems().get(i));
        }

    }


}