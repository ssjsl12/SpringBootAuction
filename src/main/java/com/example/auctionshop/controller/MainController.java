package com.example.auctionshop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    @GetMapping(value = "/main")
    public String main(Model model ,@AuthenticationPrincipal OAuth2User principal) {

        //log.info(principal.getName());

        return "main";
    }



}
