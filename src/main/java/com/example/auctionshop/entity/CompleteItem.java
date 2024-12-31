package com.example.auctionshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "completeItem")
@Getter
@Setter
public class CompleteItem {

    @Id
    @Column(name ="item_comple_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_item_id")
    private StoreItem storeItem ;

    Long count;

}
