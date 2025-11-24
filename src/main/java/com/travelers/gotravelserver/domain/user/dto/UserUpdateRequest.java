package com.travelers.gotravelserver.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

	// 선택 수정 가능 (전화번호 변경용)
	private String phone;

	// 현재 비밀번호 (필수)
	@NotBlank(message = "현재 비밀번호를 입력해주세요.")
	private String currentPassword;

	// 새 비밀번호 (필수)
	@NotBlank
	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
		message = "비밀번호는 영문자와 숫자를 각각 최소 하나 이상 포함해야 합니다."
	)
	private String newPassword;

}
