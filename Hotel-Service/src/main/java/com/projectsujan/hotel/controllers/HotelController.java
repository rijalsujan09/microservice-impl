package com.projectsujan.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectsujan.hotel.entities.Hotel;
import com.projectsujan.hotel.services.HotelService;

@RestController
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	// -> Create POST
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/add")
	public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
		Hotel savedHotel = this.hotelService.createHotel(hotel);
		return new ResponseEntity<Hotel>(savedHotel, HttpStatus.CREATED);
	}

	// -> get by id GET
//	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable String id) {
		Hotel hotel = this.hotelService.gethotel(id);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
	}

	// -> get all GET
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/all")
	public ResponseEntity<List<Hotel>> getAllhotel() {
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
	}

	// -> update PUT
	@PreAuthorize("hasAuthority('Admin')")
	@PutMapping("/update/{id}")
	public ResponseEntity<Hotel> updatHotel(@PathVariable String id, @RequestBody Hotel hotel) {
		Hotel hotel1 = this.hotelService.updateHotel(id, hotel);
		return new ResponseEntity<Hotel>(hotel1, HttpStatus.OK);
	}

	// -> Delete DELETE
	@PreAuthorize("hasAuthority('Admin')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		this.hotelService.deleteHotel(id);
		return ResponseEntity.ok("Hotel Deleted Sucessfull.");
	}
}
