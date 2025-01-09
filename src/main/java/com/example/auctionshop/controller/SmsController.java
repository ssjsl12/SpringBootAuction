package com.example.auctionshop.controller;

import com.example.auctionshop.service.CoolSmsService;
import lombok.extern.log4j.Log4j2;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sms")
@Log4j2
public class SmsController {

    @Autowired
    private CoolSmsService coolSmsService;

    @PostMapping("/send")
    public String sendSms(/*@RequestBody Map<String, String> body*/) {

        String phoneNumber = /*body.get("phoneNumber");*/ "01084971811";
    /*    try {
            String generatedCode = coolSmsService.sendSms(phoneNumber);
            return "Generated verification code: " + generatedCode;
        } catch (CoolsmsException e) {
            e.printStackTrace();
            return "Failed to send SMS: " + e.getMessage();
        }*/

        return "null";
    }

}
