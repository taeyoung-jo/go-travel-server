package com.travelers.gotravelserver.domain.user;

import java.util.HashMap;
import java.util.Map;

import com.travelers.gotravelserver.domain.user.dto.PasswordVerifyRequest;
import com.travelers.gotravelserver.domain.user.dto.PasswordVerifyResponse;
import com.travelers.gotravelserver.global.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelers.gotravelserver.domain.user.dto.EmailExistsRequest;
import com.travelers.gotravelserver.domain.user.dto.UserLoginRequest;
import com.travelers.gotravelserver.domain.user.dto.UserRegisterRequest;
import com.travelers.gotravelserver.domain.user.dto.UserResponse;
import com.travelers.gotravelserver.domain.user.dto.UserUpdateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegisterRequest req) {
		User user = userService.register(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid UserLoginRequest req,  HttpServletResponse response) {
		User user = userService.login(req);
        UserResponse userResponse = UserResponse.from(user);

        String token = jwtTokenProvider.createToken(userResponse.getEmail(),userResponse.getName());
        response.setHeader("Set-Cookie",
                "token=" + token +
                        "; Path=/; Max-Age=" + (60*30) + // 30분
                        "; HttpOnly=false; SameSite=None; Secure=false"
        );

		Map<String, Object> result = new HashMap<>();
		result.put("token", token);
		result.put("user", userResponse);

        return ResponseEntity.ok(result);
	}

	@GetMapping("/me")
	public ResponseEntity<UserResponse> getUserInfo(@RequestAttribute("user") User user) {
		return ResponseEntity.ok(UserResponse.from(user));
	}

	@PatchMapping("/me")
	public ResponseEntity<UserResponse> updateUserInfo(
		@RequestAttribute("user") User user,
		@RequestBody @Valid UserUpdateRequest req
	) {
		User updated = userService.update(user.getId(), req);
		return ResponseEntity.ok(UserResponse.from(updated));
	}

	@PostMapping("/me/password/verify")
	public ResponseEntity<PasswordVerifyResponse> verifyPassword(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody @Valid PasswordVerifyRequest req
	) {

		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new PasswordVerifyResponse(false, "로그인이 필요합니다."));
		}
		String email = userDetails.getUsername();

		boolean isValid = userService.verifyPasswordByEmail(email, req.getPassword());

		PasswordVerifyResponse response = new PasswordVerifyResponse(
			isValid,
			isValid ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다."
		);

		return ResponseEntity.ok(response);
	}

	// 이메일 중복 확인
	@PostMapping("/email-exists")
	public ResponseEntity<Boolean> checkEmailExists(@RequestBody @Valid EmailExistsRequest req) {
		boolean exists = userService.existsEmail(req.email());
		return ResponseEntity.ok(exists);
	}

}
