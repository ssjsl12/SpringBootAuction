package com.example.auctionshop.service;

import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService {


    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {

        //중복 체크
        validateDuplicateMember(member);

        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {

        Member findMember = findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("---------------loadUserByUsername--------------");

        log.info("email={}", email);

        Member member = findByEmail(email);

        log.info("member={}", member.getEmail());

        if(member == null) {
            log.info("member is null");

            throw new UsernameNotFoundException(email);
        }


        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

     public Member findByEmail(String email) {

        return memberRepository.findByEmail(email);
     }

}
