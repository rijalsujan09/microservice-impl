package com.projectsujan.hotel.services;

import java.util.List;

import com.projectsujan.hotel.entities.Hotel;

public interface HotelService {

	// -> create hotel
	Hotel createHotel(Hotel hotel);

	// -> get all hotel
	List<Hotel> getAll();

	// -> get by id
	Hotel gethotel(String id);

	// -> update hotel
	 Hotel updateHotel(String id, Hotel hotel);

	// -> delete hotel
	 void deleteHotel(String id);
}
