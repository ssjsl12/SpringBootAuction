package com.example.auctionshop.service;

import com.example.auctionshop.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private static final String title = "인증번호 안내 이메일입니다.";
    private static final String message = "안녕하세요. 인증번호 메일입니다. "
            + "\n" + "회원님의 인증번호는 아래와 같습니다." + "\n";

    private static final String title2 = "임시 비밀번호 안내 이메일입니다.";
    private static final String message2 = "안녕하세요. 임시 비밀번호 안내 메일입니다. "
            + "\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요." + "\n";


    @Value("${spring.mail.username}")
    private String from;


    //인증번호
    public MailDto createMail(String tmpPassword, String to) {
        MailDto mailDto = new MailDto(from, to, title, message + tmpPassword);
        return mailDto;
    }

    //비밀번호
    public MailDto createMail2(String tmpPassword, String to) {
        MailDto mailDto = new MailDto(from, to, title2, message2 + tmpPassword);
        return mailDto;
    }


    public void sendMail(MailDto mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getTo());
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());
        mailMessage.setFrom(mailDto.getFrom());
        mailMessage.setReplyTo(mailDto.getFrom());

        mailSender.send(mailMessage);
    }
}