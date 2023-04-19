package com.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.user.service.entities.Review;

@FeignClient(name = "REVIEW")
public interface ReviewService {
	
	// -> get 
	
	// -> post
	@PostMapping("/review/create")
	public ResponseEntity<Review> createReview(Review values);
	
	// -> put
	@PutMapping("/review/update/{reviewId}")
	public ResponseEntity<Review>  updateReview(@PathVariable("reviewId") String reviewId,Review review);
	
	// -> Delete
	@DeleteMapping("/delete/{reviewId}")
	public void deleteReview(@PathVariable String reviewId);
	
	

}
