package com.example.auctionshop.repository;

import com.example.auctionshop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Member findByEmail(String email);

    Member getMemberById(Long id);

    boolean existsByEmail(String email);

    Member findByPhone(String phone);

}
