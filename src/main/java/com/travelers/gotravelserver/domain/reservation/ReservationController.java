package com.travelers.gotravelserver.domain.reservation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelers.gotravelserver.domain.reservation.dto.ReservationCreateRequest;
import com.travelers.gotravelserver.domain.reservation.dto.ReservationDetailResponse;
import com.travelers.gotravelserver.domain.reservation.dto.ReservationResponse;
import com.travelers.gotravelserver.domain.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping("/reservations")
	public ResponseEntity<ReservationResponse> createReservation(
		@RequestAttribute("user") User user,
		@RequestBody @Valid ReservationCreateRequest request
	) {
		ReservationResponse response = reservationService.createReservation(user, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/me/reservations")
	public ResponseEntity<List<ReservationResponse>> getMyReservations(
		@RequestAttribute("user") User user
	) {
		List<ReservationResponse> responses = reservationService.getUserReservations(user);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/me/reservations/{id}")
	public ResponseEntity<ReservationDetailResponse> getReservationDetail(
		@RequestAttribute("user") User user,
		@PathVariable Long id
	) {
		ReservationDetailResponse response = reservationService.getReservationDetail(id, user);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/me/reservations/{id}")
	public ResponseEntity<Void> cancelReservation(
		@RequestAttribute("user") User user,
		@PathVariable Long id
	) {
		reservationService.cancelReservation(id, user);
		return ResponseEntity.noContent().build();
	}
}
