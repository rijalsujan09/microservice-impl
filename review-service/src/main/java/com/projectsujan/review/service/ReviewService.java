package com.projectsujan.review.service;

import java.util.List;

import com.projectsujan.review.entities.Review;

public interface ReviewService {
	
	// -> Create
	Review createReview(Review review);
	
	// -> get all review
	List<Review> getAll();
	
	// -> get all by user Id
	List<Review> getReviewByUserId(String userId);
	
	// -> get all by  Hotel
	List<Review> getReviewByHotelId(String hotelId);

}
