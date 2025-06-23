package com.likelion.crudfinal.member.api;

import com.likelion.crudfinal.common.error.SuccessCode;
import com.likelion.crudfinal.common.template.ApiResTemplate;
import com.likelion.crudfinal.member.api.dto.request.MemberJoinReqDto;
import com.likelion.crudfinal.member.api.dto.request.MemberLoginReqDto;
import com.likelion.crudfinal.member.api.dto.response.MemberInfoResDto;
import com.likelion.crudfinal.member.application.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ApiResTemplate<Void> join(@RequestBody @Valid MemberJoinReqDto memberjoinReqDto) {
        memberService.join(memberjoinReqDto);
        return ApiResTemplate.successWithNoContent(SuccessCode.MEMBER_CREATED_SUCCESS);
    }

    @PostMapping("/login")
    public ApiResTemplate<MemberInfoResDto> login(@RequestBody @Valid MemberLoginReqDto memberLoginReqDto) {
        MemberInfoResDto memberInfoResDto = memberService.login(memberLoginReqDto);
        return ApiResTemplate.successResponse(SuccessCode.LOGIN_SUCCESS, memberInfoResDto);
    }
}