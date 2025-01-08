package com.example.auctionshop.service;

import com.example.auctionshop.dto.KakaoDTO;
import org.springframework.stereotype.Service;

@Service
public interface KakaoService {

    public String getToken(String code) throws Exception;
    public KakaoDTO userInfo(String access_Token) throws Exception;
}
