package com.travelers.gotravelserver.domain.auth;

import com.travelers.gotravelserver.domain.user.User;
import com.travelers.gotravelserver.domain.user.UserService;
import com.travelers.gotravelserver.domain.user.dto.UserLoginRequest;
import com.travelers.gotravelserver.domain.user.dto.UserRegisterRequest;
import com.travelers.gotravelserver.domain.user.dto.UserResponse;
import com.travelers.gotravelserver.global.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // HTML이 열리는 주소(인텔리제이에서 여는 테스트용 브라우저)
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest req) {
        User user = userService.register(req); // User 객체
        UserResponse response = UserResponse.from(user); // 변환
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginRequest req, HttpServletResponse response) {
        User user = userService.login(req);
        UserResponse userResponse = UserResponse.from(user);
        String token = jwtTokenProvider.createToken(userResponse.getEmail());

        // 직접 Set-Cookie 헤더
        response.setHeader("Set-Cookie",
                "token=" + token +
                        "; Path=/; Max-Age=" + (60*30) +
                        "; HttpOnly=false; SameSite=None; Secure=false"
        );

        return ResponseEntity.ok().build();
    }
}


