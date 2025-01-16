package com.example.auctionshop.controller;

import com.example.auctionshop.dto.MemberFormDto;
import com.example.auctionshop.entity.ItemInventory;
import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.MemberRepository;
import com.example.auctionshop.service.InventoryService;
import com.example.auctionshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final InventoryService inventoryService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {

        model.addAttribute("memberFormDto" , new MemberFormDto());

        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberFormSubmit(@Valid MemberFormDto memberFormDto
            , BindingResult bindingResult , Model model) {

        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try
        {
            Member member = Member.createMember(memberFormDto , passwordEncoder);
            ItemInventory inventory = ItemInventory.createInventory(member);

            member.setMeso(50000);
            //멤버 저장
            memberService.saveMember(member);
            //인벤토리 저장
            inventoryService.saveInventory(inventory);
        }
        catch (IllegalStateException e)
        {
            log.info(e.getMessage());

            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }


        return "redirect:/";
    }
    @GetMapping(value = "/login")
    public String login(){


        log.info("login");

        return "member/memberloginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model)
    {
        model.addAttribute("loginErrorMsg" , "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberloginForm";
    }

    @GetMapping(value = "/option")
    public String memberOptionForm(Model model , Principal principal)
    {
        String email = principal.getName();

        Member member = memberService.findByEmail(email);

        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName(member.getName());
        memberFormDto.setEmail(email);
        memberFormDto.setAddress(member.getAddress());
        memberFormDto.setPhone(member.getPhone());

        model.addAttribute("memberFormDto" ,memberFormDto);

        return "member/memberoptionForm";
    }

    @GetMapping(value = "/changePwd")
    public String memberChangePwd(Model model, HttpSession session)
    {
        log.info("changePwd");

        String email = session.getAttribute("email").toString();

        Member member = memberService.findByEmail(email);

        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setName(member.getName());
        memberFormDto.setEmail(email);
        memberFormDto.setAddress(member.getAddress());
        memberFormDto.setPhone(member.getPhone());

        model.addAttribute("memberFormDto" ,memberFormDto);

        return "member/memberchangePwd";
    }

    @PostMapping(value = "/option")
    public String memberOptionSubmit(@Valid MemberFormDto memberFormDto)
    {
        //memberService.updatePassword(memberFormDto.getPassword(),memberFormDto.getEmail());

        memberFormDto.setName(memberFormDto.getName());
        memberFormDto.setEmail(memberFormDto.getEmail());
        memberFormDto.setAddress(memberFormDto.getAddress());
        memberFormDto.setPhone(memberFormDto.getPhone());

        Member member = memberService.findByEmail(memberFormDto.getEmail());
        memberService.updatePassword(memberFormDto.getPassword(), member.getEmail());
        member.setName(memberFormDto.getName());
        member.setAddress(memberFormDto.getAddress());

        memberService.updateMember(member);


        return "member/memberloginForm";
    }

    @GetMapping(value="/memberFind")
    public String memberFind()
    {

        return "member/memberFind";
    }



}
