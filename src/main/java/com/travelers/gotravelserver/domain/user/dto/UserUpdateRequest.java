package com.travelers.gotravelserver.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

	private String phone;

	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
	@Pattern(
		regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}:;<>?,./]).*$",
		message = "비밀번호는 숫자, 대문자, 소문자, 특수문자를 각각 최소 하나 이상 포함해야 합니다."
	)
	private String password;
}
