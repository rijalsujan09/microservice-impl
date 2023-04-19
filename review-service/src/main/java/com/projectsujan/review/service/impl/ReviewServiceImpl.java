package com.projectsujan.review.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectsujan.review.entities.Review;
import com.projectsujan.review.repositories.ReviewRepository;
import com.projectsujan.review.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Override
	public Review createReview(Review review) {
		return repository.save(review);
	}

	@Override
	public List<Review> getAll() {
		return repository.findAll();
	}

	@Override
	public List<Review> getReviewByUserId(String userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public List<Review> getReviewByHotelId(String hotelId) {
		return repository.findByHotelId(hotelId);
	}

}
