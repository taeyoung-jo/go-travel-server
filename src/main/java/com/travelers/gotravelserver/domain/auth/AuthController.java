package com.travelers.gotravelserver.domain.auth;

import com.travelers.gotravelserver.domain.user.UserService;
import com.travelers.gotravelserver.domain.user.dto.UserLoginRequest;
import com.travelers.gotravelserver.domain.user.dto.UserRegisterRequest;
import com.travelers.gotravelserver.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342") // HTML이 열리는 주소
public class AuthController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest req) {
        return ResponseEntity.ok(userService.register(req));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest req) {
        return ResponseEntity.ok(userService.login(req));
    }

}
