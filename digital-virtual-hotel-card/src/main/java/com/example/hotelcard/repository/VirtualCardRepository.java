package com.example.hotelcard.repository;

import com.example.hotelcard.model.VirtualCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualCardRepository extends JpaRepository<VirtualCard, String> {
    // No extra methods needed for now
}
