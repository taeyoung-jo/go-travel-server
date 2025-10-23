package com.travelers.gotravelserver.domain.user;

import org.hibernate.annotations.SQLDelete;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.travelers.gotravelserver.domain.user.dto.UserUpdateRequest;
import com.travelers.gotravelserver.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update users set deleted = true where id = ?")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	private String phone;

	@Builder.Default
	private Boolean deleted = Boolean.FALSE;

	public void changeInfo(UserUpdateRequest req, PasswordEncoder encoder) {
		if (req.getPhone() != null)
			this.phone = req.getPhone();
		if (req.getPassword() != null)
			this.password = encoder.encode(req.getPassword());
	}
}
