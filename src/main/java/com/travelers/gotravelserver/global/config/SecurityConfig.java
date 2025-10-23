package com.travelers.gotravelserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) // API 사용 시 CSRF 비활성화
			.formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
			.httpBasic(AbstractHttpConfigurer::disable); // 기본 인증 비활성화
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/**").permitAll() // 전체 요청 허용
		);
		return http.build();
	}
}
