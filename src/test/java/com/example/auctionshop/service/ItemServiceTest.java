package com.example.auctionshop.service;

import com.example.auctionshop.constant.ItemSellStatus;
import com.example.auctionshop.constant.ItemType;
import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemInventory;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.InventoryRepository;
import com.example.auctionshop.repository.ItemRepository;
import com.example.auctionshop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class ItemServiceTest {

    @Autowired
    public ItemRepository itemRepository;

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Commit
    @Test
    public void saveItem()
    {
        Member member = memberRepository.findByEmail("admin@test.com");
        ItemInventory inventory = inventoryRepository.findByMemberId(member.getId());

        log.info("inven : " + inventory.toString());




        for(int i = 0 ; i < 10; ++i)
        {
            Item item = new Item();
            item.setName("test" + i);
            item.setStock_number(1L + i);
            item.setType(ItemType.Equip);
            item.setStock_number(1L + i);
            item.setSellStatus(ItemSellStatus.NotSell);
            item.setInventory(inventory);

            itemRepository.save(item);
        }


    }


}