package com.example.auctionshop.controller;

import com.example.auctionshop.dto.MailDto;
import com.example.auctionshop.service.MailService;
import com.example.auctionshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/email")
@RequiredArgsConstructor
@Log4j2
public class MailController {

    private final MemberService memberService;

    @Autowired
    private MailService mailService;

    @PostMapping("/check")
    public @ResponseBody ResponseEntity checkEmail(@RequestBody Map<String , Object> request ) {

        String email = request.get("email").toString();

        log.info(email);

        if(memberService.findByEmail(email) ==  null)
        {
            return new ResponseEntity<String>("일치하는 메일이 없습니다.", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<String>("이메일을 사용하는 유저가 존재합니다.", HttpStatus.OK);
    }

    @PostMapping("/send")
    public @ResponseBody ResponseEntity sendPassword(@RequestBody Map<String , Object> request ) {


        String email = request.get("email").toString();

        log.info(email);

        String tmpPassword = memberService.getTmpPassword();
        memberService.updatePassword(tmpPassword, email);
        MailDto mail = mailService.createMail(tmpPassword, email);

        mailService.sendMail(mail);

        return new ResponseEntity<>("비밀번호 발급이 완료되었습니다.", HttpStatus.OK);
    }

}
