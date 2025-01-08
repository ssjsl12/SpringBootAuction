package com.example.auctionshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class KakaoDTO {

    private String kakaoId;
    private String nickname;
    private String kakaoEmail;

    private Map<String, Object> attributes;

    public  KakaoDTO()
    {

    }


    public KakaoDTO(String id, String nickname, Map<String, Object> attributes) {
        this.kakaoId = id;
        this.nickname = nickname;
        this.attributes = attributes;
    }

}
