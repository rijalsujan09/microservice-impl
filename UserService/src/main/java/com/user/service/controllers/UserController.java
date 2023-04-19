package com.user.service.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entities.User;
import com.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private Logger log = LoggerFactory.getLogger(UserController.class);

	// ->create user
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		User createdUser = this.userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

//	 ->get single user
	int retryCount = 1;
//	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "reviewHotelBreaker", fallbackMethod = "reviewHotelFallback")
//	@RateLimiter(name ="userRateLimiter", fallbackMethod = "reviewHotelFallback" )
//	@Retry(name = "reviewHotelService", fallbackMethod = "reviewHotelFallback" )
	
	@GetMapping("/{userId}")
	@CircuitBreaker(name = "reviewHotelBreaker", fallbackMethod = "reviewHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable String userId) {
		log.info("Retry count : {}", retryCount++);
		User userGet = this.userService.getUser(userId);
		
		return new ResponseEntity<User>(userGet, HttpStatus.OK);
	}

	// -> creating fallback method for circuit breaker and Rate Limiter
	public ResponseEntity<User> reviewHotelFallback(String userId, Exception ex) {
		
		ex.printStackTrace();
		
		log.info("Fallback is Executed because service is down : ", ex.getMessage());
		
		User user1 = this.userService.getUserById(userId);
		User user2 = User.builder()
				.email("dummy@gmail.com")
				.name("Dummy")
				.about("This is Dummy User")
				.userId("dummydata")
				.reviews(null)
				.build();
		return new ResponseEntity<User>(user2, HttpStatus.OK);

	}
	
//	// -> creating fallback method for Retry
//	public ResponseEntity<User> reviewHotelFallback(String userId, Exception ex) {
//		User user1 = this.userService.getUserById(userId);
//		User user2 = User.builder()
//				.email("dummy@gmail.com")
//				.name("Dummy")
//				.about("This is Dummy User")
//				.userId("dummydata")
//				.reviews(null)
//				.build();
//		return new ResponseEntity<User>(user2, HttpStatus.OK);
//
//	}

	// ->get single user
	@GetMapping("")
	public ResponseEntity<User> getuser(@RequestParam("userId") String userId) {
		User userGet = this.userService.getUserById(userId);
		return new ResponseEntity<User>(userGet, HttpStatus.OK);
	}

	// -> get all user
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> userList = this.userService.getAllUser();
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	// -> delete user
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<String>("user deleted sucessfully !", HttpStatus.OK);

	}

	// -> delete user
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteuser(@RequestParam String userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<String>("user deleted sucessfully !", HttpStatus.OK);

	}

	// -> update user
	@PutMapping("/update/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
		User updatedUser = this.userService.updateUser(userId, user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
}
