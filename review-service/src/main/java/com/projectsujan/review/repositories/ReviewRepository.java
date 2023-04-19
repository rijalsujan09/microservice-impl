package com.projectsujan.review.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsujan.review.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, String> {

	// Custom finder Method

	List<Review> findByUserId(String userId);

	List<Review> findByHotelId(String hotelId);
}
