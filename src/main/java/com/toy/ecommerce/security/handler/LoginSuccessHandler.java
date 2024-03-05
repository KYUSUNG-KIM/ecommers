package com.toy.ecommerce.security.handler;

import com.toy.ecommerce.security.dto.TokenDto;
import com.toy.ecommerce.security.provider.JwtTokenProvider;
import com.toy.ecommerce.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final HttpUtil httpUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String jwt = jwtTokenProvider.createToken(authentication);
        TokenDto tokenDto = new TokenDto(jwt);
        httpUtil.sendResponse(response, HttpStatus.OK, null, tokenDto);
    }
}

