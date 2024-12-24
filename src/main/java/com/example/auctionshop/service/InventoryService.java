package com.example.auctionshop.service;

import com.example.auctionshop.entity.Item;
import com.example.auctionshop.entity.ItemInventory;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class InventoryService
{
      public final InventoryRepository inventoryRepository;

      public ItemInventory saveInventory(ItemInventory inventory)
      {

          validateDuplicateMember(inventory.getMember());
          return inventoryRepository.save(inventory);
      }

    private void validateDuplicateMember(Member member) {

        ItemInventory inventory = inventoryRepository.findByMemberId(member.getId());

        if (inventory != null) {
            throw new IllegalStateException("이미 가입된 회원입니다");
        }

    }

    public List<Item> getItems(Member member)
    {
        ItemInventory inventory = inventoryRepository.findByMemberId(member.getId());

        return inventory.getItems();
    }


}
