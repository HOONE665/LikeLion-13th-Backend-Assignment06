package com.likelion.crudfinal.oauth.application;

import com.likelion.crudfinal.global.jwt.JwtTokenProvider;
import com.likelion.crudfinal.member.domain.Member;
import com.likelion.crudfinal.member.domain.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Long memberId = (Long) oAuth2User.getAttributes().get("memberId");
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        String picture = (String) oAuth2User.getAttributes().get("picture");
        String socialId = (String) oAuth2User.getAttributes().get("sub");
        String roleStr = (String) oAuth2User.getAttributes().get("role");
        Role role = Role.valueOf(roleStr);

        Member member = Member.builder()
                .memberId(memberId)
                .email(email)
                .name(name)
                .picture(picture)
                .socialId(socialId)
                .role(role)
                .password(null)
                .build();

        String jwtToken = jwtTokenProvider.generateToken(member);

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect") // 실제 프론트엔드 URL로 변경 필요
                .queryParam("token", jwtToken)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}