package com.example.auctionshop.service;

import org.springframework.stereotype.Service;

@Service
public interface KakaoService {

    public String getToken(String code) throws Exception;

}
