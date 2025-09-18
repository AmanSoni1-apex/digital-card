package com.example.hotelcard.controller;

import com.example.hotelcard.model.VirtualCard;
import com.example.hotelcard.service.VirtualCardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<VirtualCard> createCards(@RequestBody List<Map<String, Object>> requests) {
        return requests.stream()
                .map(req -> {
                    String phoneNumber = (String) req.get("phoneNumber");
                    List<String> amenities = ((List<?>) req.get("amenities"))
                            .stream()
                            .map(Object::toString)
                            .toList();
                    return service.createCard(phoneNumber, amenities);
                })
                .toList();
    }

    // Validate a card
    @PostMapping("/validate")
    public String validateCard(@RequestBody Map<String, String> request) {
        String sessionId = request.get("sessionId");
        String amenity = request.get("amenity");

        return service.validateCard(sessionId, amenity);
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
    public ResponseEntity<?> getCard(@PathVariable String sessionId) {
        VirtualCard card = service.getCard(sessionId);

        if (card == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Card not found");
    }

        // return "SessionId: " + card.getSessionId() +
        //         ", Phone: " + card.getUserId() +
        //         ", Valid From: " + card.getValidFrom() +
        //         ", Valid Till: " + card.getValidTill() +
        //         ", Amenities: " + card.getAmenitiesAllowed()+ // or format manually if you want
        //         ", suspended: "+card.getSuspended();

        return ResponseEntity.ok(card);
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
    public VirtualCard updateCard(@PathVariable String sessionId, @RequestBody Map<String, Object> request) {

        sessionId = sessionId.trim(); // removes spaces/newlines

        // Extract phoneNumber
        String phoneNumber = null;
        if (request.containsKey("phoneNumber")) {
            phoneNumber = request.get("phoneNumber").toString();
        }

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

        // Now call service with 4 args
        return service.updateCard(sessionId, phoneNumber, newAmenities, newValidTill);
    }

    // update the status of card "suspend/activate"
    @PutMapping("/{sessionId}/suspend")
    public String putMethodName(@PathVariable String sessionId, @RequestParam boolean suspend) {        
        return service.suspendCard(sessionId, suspend);
    }
}