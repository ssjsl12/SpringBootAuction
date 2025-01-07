package com.example.auctionshop.controller;

import com.example.auctionshop.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class KakaoController {

    @GetMapping("/kakaologin")
    public String kakaoConnect() {

        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + "4db3e0af6e1d6916f03390586d47359a");
        url.append("&redirect_uri=http://localhost:8080/auth/kakao/callback");
        url.append("&response_type=code");

        log.info(url.toString());

        return "redirect:" + url.toString();
    }

    @Autowired
    private KakaoService kakaoService ;

    @RequestMapping(value = "/auth/kakao/callback")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {

        String access_token = kakaoService.getToken(code);//code로 토큰 받음
        System.out.println("access_token : " + access_token);


        return "main";
    }

}
