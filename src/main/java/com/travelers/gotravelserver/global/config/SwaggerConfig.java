package com.travelers.gotravelserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info().title("여행가자 API")
				.description("여행가자 서비스의 REST API 명세서입니다.")
				.version("v0.0.1"));
	}
}
