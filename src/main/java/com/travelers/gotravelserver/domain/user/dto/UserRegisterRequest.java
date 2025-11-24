package com.travelers.gotravelserver.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterRequest {

	@Email(message = "올바른 이메일 형식을 입력해주세요.")
	@NotBlank
	private String email;

	@NotBlank
	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
		message = "비밀번호는 영문자와 숫자를 각각 최소 하나 이상 포함해야 합니다."
	)
	private String password;

	@NotBlank
	private String name;

	private String phone;
}
