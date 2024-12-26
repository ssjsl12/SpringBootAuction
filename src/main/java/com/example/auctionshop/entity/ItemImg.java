package com.example.auctionshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item_img")
@Setter
@Getter
@ToString
public class ItemImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    private String imgName; // 이미지 파일명

    private String oriImgName; //원본이미지
    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


}
