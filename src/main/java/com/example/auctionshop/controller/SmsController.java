package com.example.auctionshop.controller;

import com.example.auctionshop.entity.Member;
import com.example.auctionshop.service.CoolSmsService;
import com.example.auctionshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sms")
@Log4j2
public class SmsController {

    @Autowired
    private CoolSmsService coolSmsService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/send")
    public @ResponseBody ResponseEntity sendSms(@RequestBody Map<String, String> body , HttpSession session) {

        String phoneNumber = body.get("phoneNumber");

        try {
                String generatedCode = coolSmsService.sendSms(phoneNumber);
                session.setAttribute("generatedCode",generatedCode);
                session.setAttribute("phoneNumber",phoneNumber);
                log.info("Generated code is: " + generatedCode);



                 return new ResponseEntity<String>("인증번호 요청이 완료되었습니다..", HttpStatus.OK);
            }
        catch (Exception e)
        {

            e.printStackTrace();

            return new ResponseEntity<String>("인증번호 요청을 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }



    }

    @PostMapping("/verify")
    public @ResponseBody ResponseEntity verifySms(@RequestBody Map<String, String> request , HttpSession session)
    {
        String inputCode = request.get("code");
        String authMessage = (String) session.getAttribute("generatedCode");
        String phoneNumber = session.getAttribute("phoneNumber").toString();

        Member member = memberService.findByPhoeNumber(phoneNumber);
        session.setAttribute("email", member.getEmail());

        if (authMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 만료되었거나 잘못되었습니다.");
        }

        if (authMessage.equals(inputCode)) {

            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "인증번호가 일치합니다.", "redirectUrl", "members/changePwd"));
        }
        else{

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 올바르지 않습니다.");
         }

    }

}
