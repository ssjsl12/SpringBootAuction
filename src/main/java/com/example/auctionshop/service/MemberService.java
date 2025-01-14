package com.example.auctionshop.service;

import com.example.auctionshop.entity.Member;
import com.example.auctionshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService {


    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public boolean emailExist(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Member findByPhoeNumber(String phoeNumber) {
        return memberRepository.findByPhone(phoeNumber);
    }

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

     public void updateMemberMeso(Member member ,int itemPrice) {
        member.setMeso(member.getMeso() - itemPrice);
        memberRepository.save(member);
     }

     public void updateMember(Member member)
     {
         memberRepository.save(member);
     }

    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String newPassword = "";

        for (int i = 0; i < 10; i++) {
            int idx = (int) (charSet.length * Math.random());
            newPassword += charSet[idx];
        }

        return newPassword;
    }

    @Transactional
    public void updatePassword(String tmpPassword, String email) {

        Member user = memberRepository.findByEmail(email);

        user.setPassword(passwordEncoder.encode(tmpPassword));
    }

}
