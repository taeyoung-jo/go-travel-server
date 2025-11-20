package com.travelers.gotravelserver.global.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = jwtTokenProvider.resolveToken(request);
		if (token != null && jwtTokenProvider.validateToken(token)) {
			String email = jwtTokenProvider.getEmail(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken auth =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(auth);
			request.setAttribute("user", ((CustomUserDetails)userDetails).getUser());
		}

		filterChain.doFilter(request, response);
	}
}
