package com.travelers.gotravelserver.domain.reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.flight.FlightService;
import com.travelers.gotravelserver.domain.flight.domain.Flight;
import com.travelers.gotravelserver.domain.product.ProductService;
import com.travelers.gotravelserver.domain.product.domain.Product;
import com.travelers.gotravelserver.domain.product.domain.ProductStatus;
import com.travelers.gotravelserver.domain.reservation.domain.Reservation;
import com.travelers.gotravelserver.domain.reservation.domain.ReservationStatus;
import com.travelers.gotravelserver.domain.reservation.dto.ReservationDetailResponse;
import com.travelers.gotravelserver.domain.reservation.dto.ReservationResponse;
import com.travelers.gotravelserver.domain.user.User;
import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final FlightService flightService;
	private final ProductService productService;

	// 예약 생성
	@Transactional
	public ReservationResponse createReservation(Long productId, User user, int participants, LocalDate departureDate) {
		// 상품 상태 검증
		Product product = productService.getProductById(productId);
		if (product.getStatus() != ProductStatus.AVAILABLE)
			throw new CustomException(ErrorCode.PRODUCT_NOT_AVAILABLE);

		// 좌석 차감, 0석 되면 상태 변경
		product.decreaseSeats(participants);
		if (product.getSeats() == 0)
			product.changeStatus(ProductStatus.SOLD_OUT);

		// 가격 계산
		Flight deptFlight = flightService.getCheapestFlight(product.getLocation(), departureDate);
		Flight returnFlight = flightService.getCheapestFlight(
			product.getLocation(), departureDate.plusDays(product.getDays()));

		BigDecimal totalPrice = product.getPrice()
			.add(deptFlight.getPrice())
			.add(returnFlight.getPrice())
			.multiply(BigDecimal.valueOf(participants));

		// 예약 생성
		Reservation reservation = Reservation.builder()
			.user(user)
			.product(product)
			.flight(deptFlight)
			.participants(participants)
			.price(totalPrice)
			.status(ReservationStatus.PENDING)
			.build();
		Reservation saved = reservationRepository.save(reservation);
		return ReservationResponse.from(saved);
	}

	// 유저별 예약 목록 조회
	@Transactional(readOnly = true)
	public List<ReservationResponse> getUserReservations(User user) {
		return reservationRepository.findAllByUserAndDeletedFalse(user).stream()
			.map(ReservationResponse::from)
			.toList();
	}

	// 예약 상세 조회
	@Transactional(readOnly = true)
	public ReservationDetailResponse getReservationDetail(Long reservationId, User user) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
		if (!reservation.getUser().equals(user))
			throw new CustomException(ErrorCode.NO_AUTHORITY);
		return ReservationDetailResponse.from(reservation);
	}

	// 예약 취소
	public void cancelReservation(Long reservationId, User user) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
		if (!reservation.getUser().equals(user))
			throw new CustomException(ErrorCode.NO_AUTHORITY);
		if (reservation.getStatus() == ReservationStatus.CANCELLED)
			throw new CustomException(ErrorCode.RESERVATION_ALREADY_CANCELLED);

		// 예약 상태 복원
		reservation.changeStatus(ReservationStatus.CANCELLED);
		// 좌석 복원
		reservation.getProduct().increaseSeats(reservation.getParticipants());
		// 상품 상태 복구
		if (reservation.getProduct().getStatus() == ProductStatus.SOLD_OUT &&
			reservation.getProduct().getSeats() > 0) {
			reservation.getProduct().changeStatus(ProductStatus.AVAILABLE);
		}
	}
}
