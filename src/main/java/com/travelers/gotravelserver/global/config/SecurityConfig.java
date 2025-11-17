package com.travelers.gotravelserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) // API 사용 시 CSRF 비활성화
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
			.httpBasic(AbstractHttpConfigurer::disable); // 기본 인증 비활성화
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/**").permitAll() // 전체 요청 허용
			.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll());
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("http://localhost:5173");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
