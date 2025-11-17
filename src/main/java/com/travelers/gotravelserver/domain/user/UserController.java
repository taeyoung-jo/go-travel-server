package com.travelers.gotravelserver.domain.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegisterRequest req) {
		User user = userService.register(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody @Valid UserLoginRequest req) {
		User user = userService.login(req);
		return ResponseEntity.ok(UserResponse.from(user));
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
}
