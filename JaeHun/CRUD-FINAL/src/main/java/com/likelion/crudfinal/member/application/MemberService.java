package com.likelion.crudfinal.member.application;

import com.likelion.crudfinal.common.error.ErrorCode;
import com.likelion.crudfinal.common.exception.BusinessException;
import com.likelion.crudfinal.global.jwt.JwtTokenProvider;
import com.likelion.crudfinal.member.api.dto.request.MemberJoinReqDto;
import com.likelion.crudfinal.member.api.dto.request.MemberLoginReqDto;
import com.likelion.crudfinal.member.api.dto.response.MemberInfoResDto;
import com.likelion.crudfinal.member.domain.Member;
import com.likelion.crudfinal.member.domain.Role;
import com.likelion.crudfinal.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void join(MemberJoinReqDto memberJoinReqDto) {
        if (memberRepository.existsByEmail(memberJoinReqDto.email()))
            throw new BusinessException(ErrorCode.ALREADY_EXISTS_EMAIL, ErrorCode.ALREADY_EXISTS_EMAIL.getMessage());

        Member member = Member.builder()
                .email(memberJoinReqDto.email())
                .password(passwordEncoder.encode(memberJoinReqDto.password()))
                .name(memberJoinReqDto.name())
                .role(Role.ROLE_USER)
                .socialId(null)
                .picture(null)
                .build();

        memberRepository.save(member);
    }

    public MemberInfoResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION, ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()));

        if(!passwordEncoder.matches(memberLoginReqDto.password(), member.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        String token = jwtTokenProvider.generateToken(member);

        return MemberInfoResDto.of(member, token);
    }

    @Transactional
    public Member saveOrUpdateSocialMember(String email, String name, String picture, String socialId) {
        Member member = memberRepository.findBySocialId(socialId)
                .map(entity -> entity.update(name, picture))
                .orElseGet(() -> Member.builder()
                        .email(email)
                        .name(name)
                        .picture(picture)
                        .socialId(socialId)
                        .role(Role.ROLE_USER)
                        .password(null)
                        .build());

        return memberRepository.save(member);
    }
}