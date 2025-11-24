package com.travelers.gotravelserver.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.user.dto.UserLoginRequest;
import com.travelers.gotravelserver.domain.user.dto.UserRegisterRequest;
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
	public User register(UserRegisterRequest req) {
		if (userRepository.existsByEmail(req.getEmail()))
			throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
		User user = User.builder()
			.email(req.getEmail())
			.password(passwordEncoder.encode(req.getPassword()))
			.name(req.getName())
			.phone(req.getPhone())
			.build();
		return userRepository.save(user);
	}

	// 로그인
	public User login(UserLoginRequest req) {
		User user = userRepository.findByEmail(req.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}
		return user;
	}

	// 이메일로 사용자 확인
	public boolean verifyPasswordByEmail(String email, String rawPassword) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		return passwordEncoder.matches(rawPassword, user.getPassword());
	}

	// 회원정보 수정 - phone 또는 password
	@Transactional
	public User update(Long id, UserUpdateRequest req) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		user.changeInfo(req, passwordEncoder);
		return userRepository.save(user);
	}

	public boolean existsEmail(String email) {
		return userRepository.existsByEmail(email);
	}
}
