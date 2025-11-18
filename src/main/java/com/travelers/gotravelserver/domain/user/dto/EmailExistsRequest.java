package com.travelers.gotravelserver.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailExistsRequest(
	@NotBlank
	@Email
	String email
) {
}
