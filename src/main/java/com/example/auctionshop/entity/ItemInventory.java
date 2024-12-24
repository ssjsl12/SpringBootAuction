package com.example.auctionshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_inventory")
@Getter
@Setter
@ToString
public class ItemInventory
{
    @Id
    @Column(name = "item_inventory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();


    public static ItemInventory createInventory(Member member) {
        ItemInventory inventory = new ItemInventory();
        inventory.member = member;
        return inventory;
    }

}
