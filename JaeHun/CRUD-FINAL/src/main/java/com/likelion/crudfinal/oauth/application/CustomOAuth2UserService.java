package com.likelion.crudfinal.oauth.application;

import com.likelion.crudfinal.member.application.MemberService;
import com.likelion.crudfinal.member.domain.Member;
import com.likelion.crudfinal.oauth.api.dto.GoogleOAuth2UserInfo;
import com.likelion.crudfinal.oauth.api.dto.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo;

        if ("google".equals(registrationId)) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다: " + registrationId);
        }

        String socialId = oAuth2UserInfo.getId();
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();
        String picture = oAuth2UserInfo.getPicture();

        Member member = memberService.saveOrUpdateSocialMember(email, name, picture, socialId);

        Map<String, Object> updatedAttributes = new java.util.HashMap<>(oAuth2User.getAttributes());
        updatedAttributes.put("memberId", member.getMemberId());
        updatedAttributes.put("role", member.getRoleEnum().name());
        updatedAttributes.put("email", member.getEmail());

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(member.getRoleEnum().name())),
                updatedAttributes,
                userNameAttributeName
        );
    }
}