package com.travelers.gotravelserver.global.config;

import com.travelers.gotravelserver.global.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    // 비밀번호 암호화용 Bean (BCrypt 알고리즘 사용)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    // 인증 매니저(스프링 시큐리티 인증 핵심 부분)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 시큐리티 필터 설정 (요청별 인증/인가 규칙 정의)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) // API 사용 시 CSRF 비활성화
			.formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
			.httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 비활성화
		    .authorizeHttpRequests(auth -> auth
			    .requestMatchers("/auth/**").permitAll() // 회원가입/로그인은 허용
                .anyRequest().authenticated() // 그 외는 인증 필요
		);
		return http.build();
	}
}
