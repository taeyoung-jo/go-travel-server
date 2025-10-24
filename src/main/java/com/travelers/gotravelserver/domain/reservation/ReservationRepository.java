package com.travelers.gotravelserver.domain.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travelers.gotravelserver.domain.reservation.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findByUserId(Long userId); // 사용자별 예약 목록 조회

	@Query("select coalesce(sum(r.participants), 0) from Reservation r where r.product.id = :productId")
	int getTotalParticipantsByProductId(Long productId); // 특정 상품의 현재 예약 인원 수
}
