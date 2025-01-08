package com.example.auctionshop.controller;


import com.example.auctionshop.constant.Role;
import com.example.auctionshop.dto.KakaoDTO;
import com.example.auctionshop.entity.ItemInventory;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.service.InventoryService;
import com.example.auctionshop.service.KakaoService;
import com.example.auctionshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@Log4j2
public class KakaoController {


    @Autowired
    private KakaoService kakaoService ;

    @Autowired
    private MemberService memberService;
    @Autowired
    private InventoryService inventoryService;


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

    @RequestMapping(value = "/auth/kakao/callback")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {

        String access_token = kakaoService.getToken(code); // code로 토큰 받음
        System.out.println("access_token : " + access_token);

        KakaoDTO user = kakaoService.userInfo(access_token);

        OAuth2User oauth2User = new DefaultOAuth2User(
                Collections.singletonList(new OAuth2UserAuthority("ROLE_ADMIN", user.getAttributes())),
                user.getAttributes(),
                "id"
        );

        // Authentication 객체를 만들고 SecurityContext에 설정
        Authentication authentication = new OAuth2AuthenticationToken(oauth2User, oauth2User.getAuthorities(), "kakao");

        // SecurityContext를 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        // SecurityContextHolder에 설정된 SecurityContext를 세션에 저장
        SecurityContextHolder.setContext(context);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

        if(memberService.findByEmail(user.getKakaoId().toString()) != null)
        {
            return "redirect:/item/search";
        }

        Member member = new Member();
        member.setEmail(user.getKakaoId());
        member.setRole(Role.ADMIN);
        member.setPassword(null);
        member.setName(user.getNickname());
        member.setMeso(500000);

        ItemInventory inventory = ItemInventory.createInventory(member);

        memberService.saveMember(member);
        inventoryService.saveInventory(inventory);

        return "redirect:/item/search";
    }

}
