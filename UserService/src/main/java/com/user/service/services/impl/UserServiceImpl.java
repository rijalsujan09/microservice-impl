package com.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entities.Hotel;
import com.user.service.entities.Review;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.external.services.HotelServices;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;

import feign.Request.HttpMethod;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HotelServices hotelServices;

	@Autowired
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		User savedUser = this.userRepository.save(user);
		return savedUser;
	}

	@Override
	public List<User> getAllUser() {
		List<User> list = this.userRepository.findAll();
		return list;
	}

	@Override
	public User getUserById(String userId) {
		User user1 = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("No user found with id" + userId));
		return user1;
	}

	@Override
	public User getUser(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given Id not found on Server --> " + userId));

		// fetching review data from Review Service
		// http://localhost:8083/review/user/dc4b43a1-d55a-4232-9b62-ad1f6509e221
		// http://REVIEW/review/user/dc4b43a1-d55a-4232-9b62-ad1f6509e221
		Review[] reviewByUser = restTemplate.getForObject("http://REVIEW/review/user/" + userId,Review[].class);

		List<Review> reviews = Arrays.stream(reviewByUser).toList();
		List<Review> reviewList = reviews.stream().map(review -> {

			// fetching hotel data from hotel Service
			// http://localhost:8082/hotel/9cc572a6-c72d-42e7-a5df-6275844a1d3f

//			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL/hotel/" + review.getHotelId(),
//					Hotel.class);

			// -> Feign Client Approach
			Hotel hotel = hotelServices.getHotel(review.getHotelId());

			review.setHotel(hotel);
			return review;
		}).collect(Collectors.toList());

		user.setReviews(reviewList);
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		this.userRepository.deleteById(userId);
		return;
	}

	@Override
	public User updateUser(String userId, User user) {
		User user1 = this.userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given Id not found on Server --> " + userId));
		user1.setName(user.getName());
		user1.setEmail(user.getEmail());
		user1.setAbout(user.getAbout());
		User updatedUser = this.userRepository.saveAndFlush(user1);
		return updatedUser;
	}

}
