package com.example.auctionshop.entity;

import com.example.auctionshop.constant.Role;
import com.example.auctionshop.entity.BaseEntity;
import com.example.auctionshop.constant.Role;
import com.example.auctionshop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member extends BaseEntity
{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;


    public static Member createMember(MemberFormDto dto, PasswordEncoder passwordEncoder)
    {
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        member.setAddress(dto.getAddress());
        member.setRole(Role.ADMIN);

        return member;
    }
}
