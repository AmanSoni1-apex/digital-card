package com.example.hotelcard.controller;

import com.example.hotelcard.model.VirtualCard;
import com.example.hotelcard.service.VirtualCardService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
public class VirtualCardController {

    private final VirtualCardService service;

    public VirtualCardController(VirtualCardService service) {
        this.service = service;
    }

    /*
     * Create a new card
     * C → Create
     * POST /api/cards/create → Creates a new virtual card.
     */

    @PostMapping("/create")
    public VirtualCard createCard(@RequestBody Map<String, Object> request) {
        String phoneNumber = (String) request.get("phoneNumber");

        // Convert amenities safely
        List<String> amenities = ((List<?>) request.get("amenities"))
                .stream()
                .map(Object::toString)
                .toList();

        return service.createCard(phoneNumber, amenities);
    }

    // Validate a card
    @PostMapping("/validate")
    public String validateCard(@RequestBody Map<String, String> request) {
        String sessionId = request.get("sessionId");
        String amenity = request.get("amenity");

        boolean valid = service.validateCard(sessionId, amenity);
        return valid ? "Access granted for " + amenity : "Access denied";
    }
    /*
     * R → Read
     * GET/api/cards/{sessionId}→
     * Get a
     * single card
     * by its sessionId.
     * GET/api/cards/all→
     * Get all
     * 
     * cards (admin view).
     */

    @GetMapping("/{sessionId}")
    public VirtualCard getCard(@PathVariable String sessionId) {
        return service.getCard(sessionId);
    }

    // Get all cards (Admin)
    @GetMapping("/all")
    public List<VirtualCard> getAllCards() {
        return service.getAllCards();
    }

    /*
     * / Delete a card by sessionId
     */
    @DeleteMapping("/{sessionId}")
    public String deleteCard(@PathVariable String sessionId) {
        return service.deleteCard(sessionId.trim());
    }

    // Update a card
@PutMapping("/{sessionId}")
public VirtualCard updateCard(
        @PathVariable String sessionId,
        @RequestBody Map<String, Object> request) {

    sessionId = sessionId.trim(); // removes spaces/newlines

    // Safely extract amenities
    List<String> newAmenities = null;
    if (request.containsKey("amenities")) {
        Object amenitiesObj = request.get("amenities");
        if (amenitiesObj instanceof List<?>) {
            newAmenities = ((List<?>) amenitiesObj)
                    .stream()
                    .map(Object::toString)
                    .toList();
        }
    }

    // Safely extract validTill
    LocalDateTime newValidTill = null;
    if (request.containsKey("validTill")) {
        try {
            String validTillStr = request.get("validTill").toString();
            newValidTill = LocalDateTime.parse(validTillStr);
        } catch (Exception e) {
            throw new RuntimeException("Invalid validTill format. Use yyyy-MM-ddTHH:mm:ss");
        }
    }

    return service.updateCard(sessionId, newAmenities, newValidTill);
}
}



