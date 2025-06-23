package com.likelion.crudfinal.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;

    private String password;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_picture_url")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private Role role;

    @Column(unique = true)
    private String socialId;

    @Builder
    public Member(Long memberId, String email, String password, String name, String picture, Role role, String socialId) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.picture = picture;
        this.role = role;
        this.socialId = socialId;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public Role getRoleEnum() {
        return this.role;
    }
}