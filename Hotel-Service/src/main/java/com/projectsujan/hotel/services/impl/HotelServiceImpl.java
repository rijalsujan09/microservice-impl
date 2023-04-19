package com.projectsujan.hotel.services.impl;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.projectsujan.hotel.entities.Hotel;
import com.projectsujan.hotel.exceptions.ResourceNotFoundException;
import com.projectsujan.hotel.repositories.HotelRepository;
import com.projectsujan.hotel.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public Hotel createHotel(Hotel hotel) {
		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel gethotel(String id) {
		Hotel hotel = this.hotelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not found -> " + id));
		return hotel;

	}

	@Override
	public Hotel updateHotel(String id, Hotel hotel) {
		Hotel hotel1 = this.hotelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not Found."));
		hotel1.setAbout(hotel.getAbout());
		hotel1.setLocation(hotel.getLocation());
		hotel1.setName(hotel.getName());
		return this.hotelRepository.saveAndFlush(hotel1);
	}

	@Override
	public void deleteHotel(String id) {
		this.hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		this.hotelRepository.deleteById(id);
		return;
	}

}
