package com.example.hotelcard.service;

import com.example.hotelcard.model.VirtualCard;
import com.example.hotelcard.repository.VirtualCardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VirtualCardService {

    private final VirtualCardRepository repository;

    public VirtualCardService(VirtualCardRepository repository) {
        this.repository = repository;
    }

    // Create a new card
    public VirtualCard createCard(String userId, String guestName, List<String> amenities) {

        VirtualCard card = new VirtualCard();
        card.setSessionId(UUID.randomUUID().toString());
        card.setUserId(userId);
        card.setGuestName(guestName);
        card.setValidFrom(LocalDateTime.now());
        card.setValidTill(LocalDateTime.now().plusDays(1)); // 1 day validity
        card.setAmenitiesAllowed(amenities);
        return repository.save(card);
    }

    // get all cards
    public List<VirtualCard> getAllCards() {
        return repository.findAll();
    }

    // get a specific card 
    public VirtualCard getCardBySessionId(String sessionId) {
        return repository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Card not found with ID: " + sessionId));
    }

    // Validate card
    public String validateCard(String sessionId, String amenity) {
        VirtualCard card = repository.findById(sessionId).orElse(null);
        if (card == null)
            return "Access denied: Card not found";

        LocalDateTime now = LocalDateTime.now();

        // Check expiry
        if (now.isBefore(card.getValidFrom()) || now.isAfter(card.getValidTill())) {
            return "Access denied: Card expired";
        }

        // Check amenity access
        if (!card.getAmenitiesAllowed().contains(amenity)) {
            return "Access denied: Amenity not allowed";
        }
        // Check amenity access
        return "Access granted for " + amenity;
    }

    // Get card by sessionId.
    public VirtualCard getCard(String sessionId) {
        return repository.findById(sessionId).orElse(null);
    }


    // Delete card.
    public String deleteCard(String sessionId) {
        if (repository.existsById(sessionId)) {
            repository.deleteById(sessionId);
            return "Card with sessionId " + sessionId + " deleted successfully.";
        } else {
            return "Card with sessionId " + sessionId + " not found.";
        }
    }


    // update a card 
    public VirtualCard updateCard(String sessionId, String phoneNumber, List<String> newAmenities,
            LocalDateTime newValidTill) {
        VirtualCard card = repository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            card.setUserId(phoneNumber); // phoneNumber → userId
        }
        if (newAmenities != null && !newAmenities.isEmpty()) {
            // Force mutable list
            card.setAmenitiesAllowed(new ArrayList<>(newAmenities));
        }

        if (newValidTill != null) {
            card.setValidTill(newValidTill);
        }

        return repository.save(card); // Hibernate → UPDATE in DB
    }

    // suspend a card by the sessionId
    public String suspendCard(String sessionId, boolean suspend) {
        VirtualCard card = repository.findById(sessionId).orElse(null);
        if (card == null) {
            return "card not found";
        }
        card.setSuspended(suspend);
        repository.save(card);
        return suspend ? "Card suspended successfully" : "Card reactivated successfully";
    }

}
