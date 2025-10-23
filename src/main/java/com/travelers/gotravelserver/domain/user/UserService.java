package com.travelers.gotravelserver.domain.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.user.dto.UserLoginRequest;
import com.travelers.gotravelserver.domain.user.dto.UserRegisterRequest;
import com.travelers.gotravelserver.domain.user.dto.UserResponse;
import com.travelers.gotravelserver.domain.user.dto.UserUpdateRequest;
import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// 회원가입
	@Transactional
	public UserResponse register(UserRegisterRequest req) {
		if (userRepository.existsByEmail(req.getEmail()))
			throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
		User user = User.builder()
			.email(req.getEmail())
			.password(passwordEncoder.encode(req.getPassword()))
			.name(req.getName())
			.phone(req.getPhone())
			.build();
		return UserResponse.from(userRepository.save(user));
	}

	// 로그인
	public UserResponse login(UserLoginRequest req) {
		User user = userRepository.findByEmail(req.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		return UserResponse.from(user);
	}

	// 회원정보 수정 - phone 또는 password
	@Transactional
	public UserResponse update(Long id, UserUpdateRequest req) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		user.changeInfo(req, passwordEncoder);
		return UserResponse.from(userRepository.save(user));
	}

	// ID로 사용자 조회
	public UserResponse getUserById(Long id) {
		return UserResponse.from(findUser(id));
	}

	// 이메일로 사용자 조회
	public UserResponse getUserByEmail(String email) {
		return UserResponse.from(
			userRepository.findByEmail(email)
				.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND))
		);
	}

	// 전화번호로 사용자 조회
	public UserResponse getUserByPhone(String phone) {
		return UserResponse.from(
			userRepository.findByPhone(phone)
				.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
	}

	// 사용자 전체 조회
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll()
			.stream()
			.map(UserResponse::from)
			.toList();
	}

	private User findUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}
