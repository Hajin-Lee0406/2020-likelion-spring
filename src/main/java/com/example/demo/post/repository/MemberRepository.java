package com.example.demo.post.repository;

import com.example.demo.post.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String userEmail);

    boolean existsByEmail(String email);
}
