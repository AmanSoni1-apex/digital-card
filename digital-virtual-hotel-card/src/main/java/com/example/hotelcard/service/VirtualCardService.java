package com.example.hotelcard.service;

import com.example.hotelcard.model.VirtualCard;
import com.example.hotelcard.repository.VirtualCardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VirtualCardService {

    private final VirtualCardRepository repository;

    public VirtualCardService(VirtualCardRepository repository) {
        this.repository = repository;
    }

    // Create a new card
    public VirtualCard createCard(String userId, List<String> amenities) {
       
        VirtualCard card = new VirtualCard();
        card.setSessionId(UUID.randomUUID().toString());
        card.setUserId(userId);
        card.setValidFrom(LocalDateTime.now());
        card.setValidTill(LocalDateTime.now().plusDays(1)); // 1 day validity
        card.setAmenitiesAllowed(amenities);
        return repository.save(card);
    }

    // Validate card
    public boolean validateCard(String sessionId, String amenity) {
        VirtualCard card = repository.findById(sessionId).orElse(null);
        if (card == null) return false;

        LocalDateTime now = LocalDateTime.now();

        // Check expiry
        if (now.isBefore(card.getValidFrom()) || now.isAfter(card.getValidTill())) {
            return false;
        }

        // Check amenity access
        return card.getAmenitiesAllowed().contains(amenity);
    }

    // Get card by sessionId
    public VirtualCard getCard(String sessionId) {
        return repository.findById(sessionId).orElse(null);
    }
}
