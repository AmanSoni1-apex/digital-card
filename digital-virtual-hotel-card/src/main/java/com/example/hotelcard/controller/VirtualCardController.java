package com.example.hotelcard.controller;

import com.example.hotelcard.model.VirtualCard;
import com.example.hotelcard.service.VirtualCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
public class VirtualCardController {

    private final VirtualCardService service;

    public VirtualCardController(VirtualCardService service) {
        this.service = service;
    }

    // Create a new card
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

    @GetMapping("/{sessionId}")
    public VirtualCard getCard(@PathVariable String sessionId) {
        return service.getCard(sessionId);
    }
}

