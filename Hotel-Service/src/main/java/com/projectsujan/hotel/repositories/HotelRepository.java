package com.projectsujan.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsujan.hotel.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

}
