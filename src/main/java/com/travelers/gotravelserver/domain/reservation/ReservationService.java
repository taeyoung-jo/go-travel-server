package com.travelers.gotravelserver.domain.reservation;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.product.ProductService;
import com.travelers.gotravelserver.domain.product.domain.Product;
import com.travelers.gotravelserver.domain.product.domain.ProductStatus;
import com.travelers.gotravelserver.domain.reservation.domain.Reservation;
import com.travelers.gotravelserver.domain.reservation.domain.ReservationStatus;
import com.travelers.gotravelserver.domain.user.User;
import com.travelers.gotravelserver.domain.user.UserService;
import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final UserService userService;
	private final ProductService productService;

	@Transactional
	public Reservation createReservation(Long userId, Long productId, int participants) {
		User user = userService.getUserById(userId);
		Product product = productService.getProductById(productId);

		// 상품 상태 확인
		if (product.getStatus() != ProductStatus.AVAILABLE)
			throw new CustomException(ErrorCode.PRODUCT_NOT_AVAILABLE);

		// 잔여 좌석 확인
		int reservedCount = reservationRepository.getTotalParticipantsByProductId(productId);
		int remaining = product.getMaxParticipants() - reservedCount;
		if (remaining - participants < 0)
			throw new CustomException(ErrorCode.INSUFFICIENT_REMAINING_SEATS);

		// 예약 생성
		Reservation reservation = Reservation.builder()
			.user(user)
			.product(product)
			.participants(participants)
			.status(ReservationStatus.PENDING)
			.build();
		reservationRepository.save(reservation);

		// 잔여 좌석이 0이면 상품 상태 변경
		int newRemaining = remaining - participants;
		if (newRemaining == 0)
			productService.updateProductStatus(productId, ProductStatus.SOLD_OUT);
		return reservation;
	}

	public Reservation getReservationById(Long id) {
		return reservationRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
	}

	public List<Reservation> getReservationsByUserId(Long userId) {
		return reservationRepository.findByUserId(userId);
	}

	@Transactional
	public void cancelReservation(Long id) {
		Reservation reservation = getReservationById(id);
		reservation.changeStatus(ReservationStatus.CANCELLED);
		productService.updateProductStatus(reservation.getProduct().getId(), ProductStatus.AVAILABLE);
		reservationRepository.deleteById(id);
	}
}
