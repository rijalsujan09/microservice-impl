package com.projectsujan.review.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectsujan.review.entities.Review;
import com.projectsujan.review.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	// POST -> Create Review
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/create")
	public ResponseEntity<Review> create(@RequestBody Review review) {
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(review));
	}
	
	// GET -> get All Review
	@GetMapping("/all")
	public ResponseEntity<List<Review>> getAllRatings(){
		return ResponseEntity.ok(reviewService.getAll());
	}
	
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getAllRatingsByUserId(@PathVariable String userId){
		return ResponseEntity.ok(reviewService.getReviewByUserId(userId));
	}

	
	@GetMapping("/hotel/{hotelId}")
	public ResponseEntity<List<Review>> getAllRatingsByHotelId(@PathVariable String hotelId){
		return ResponseEntity.ok(reviewService.getReviewByHotelId(hotelId));
	}


}
