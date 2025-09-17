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
        if (card == null)
            return false;

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

    public List<VirtualCard> getAllCards() {
        return repository.findAll();
    }

    public String deleteCard(String sessionId) {
        if (repository.existsById(sessionId)) {
            repository.deleteById(sessionId);
            return "Card with sessionId " + sessionId + " deleted successfully.";
        } else {
            return "Card with sessionId " + sessionId + " not found.";
        }
    }
public VirtualCard updateCard(String sessionId, List<String> newAmenities, LocalDateTime newValidTill) {
    VirtualCard card = repository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Card not found"));

    if (newAmenities != null && !newAmenities.isEmpty()) {
        // Force mutable list
        card.setAmenitiesAllowed(new ArrayList<>(newAmenities));
    }

    if (newValidTill != null) {
        card.setValidTill(newValidTill);
    }

    return repository.save(card); // Hibernate → UPDATE in DB
}

}
