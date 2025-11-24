package com.travelers.gotravelserver.domain.reservation;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travelers.gotravelserver.domain.reservation.domain.Reservation;
import com.travelers.gotravelserver.domain.user.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	// 유저별 예약 목록 조회
	Optional<Reservation> findByIdAndUser(Long id, User user);

	@Query("select r from Reservation r "
		+ "join fetch r.product p "
		+ "join fetch r.flight f "
		+ "where r.user = :user and r.deleted = false")
	List<Reservation> findAllByUserAndDeletedFalse(@Param("user") User user);

	@Query("select r from Reservation r "
		+ "join fetch r.product p "
		+ "join fetch p.location l "
		+ "where r.id = :id and r.deleted = false")
	Optional<Reservation> findWithProductAndLocation(@Param("id") Long id);

	@Query("select r from Reservation r "
		+ "join fetch r.product p "
		+ "join fetch p.location l "
		+ "join fetch r.flight f "
		+ "where r.id = :id and r.deleted = false")
	Optional<Reservation> findWithDetailsById(@Param("id") Long id);
}
