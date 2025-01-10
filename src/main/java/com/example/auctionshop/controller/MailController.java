package com.example.auctionshop.controller;

import com.example.auctionshop.dto.MailDto;
import com.example.auctionshop.service.MailService;
import com.example.auctionshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
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
    public @ResponseBody ResponseEntity sendAuthMessage(@RequestBody Map<String , Object> request, HttpSession session) {


        String email = request.get("email").toString();

        String tmpPassword = memberService.getTmpPassword();

        session.setAttribute("tmpPassword", tmpPassword);

        //memberService.updatePassword(tmpPassword, email);
        MailDto mail = mailService.createMail(tmpPassword, email);

        mailService.sendMail(mail);

        return new ResponseEntity<>("인증번호 요청이 완료되었습니다..", HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> request, HttpSession session) {
        String inputCode = request.get("code");
        String email = request.get("email").toString();
        // 세션에서 임시 비밀번호 가져오기
        String tmpPassword = (String) session.getAttribute("tmpPassword");

        if (tmpPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 만료되었거나 잘못되었습니다.");
        }

        // 클라이언트 입력 값과 대조
        if (tmpPassword.equals(inputCode)) {
            session.removeAttribute("tmpPassword");

            tmpPassword = memberService.getTmpPassword();
            memberService.updatePassword(tmpPassword, email);
            MailDto mail = mailService.createMail2(tmpPassword, email);
            mailService.sendMail(mail);

            return ResponseEntity.ok("인증번호가 일치합니다");
        } else {
            // 인증 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 올바르지 않습니다.");
        }
    }

}
