package com.travelers.gotravelserver.domain.user.dto;

import com.travelers.gotravelserver.domain.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private Long id;
	private String email;
	private String name;
	private String phone;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.phone(user.getPhone())
			.build();
	}
}
