package com.likelion.crudfinal.member.domain.repository;

import com.likelion.crudfinal.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findBySocialId(String socialId);
}