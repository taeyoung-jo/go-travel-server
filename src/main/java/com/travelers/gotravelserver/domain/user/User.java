package com.travelers.gotravelserver.domain.user;

import org.hibernate.annotations.SQLDelete;

import com.travelers.gotravelserver.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update users set deleted = true where id = ?")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "phone")
	private String phone;

	@Builder.Default
	@Column(name = "deleted")
	private Boolean deleted = Boolean.FALSE;
}
