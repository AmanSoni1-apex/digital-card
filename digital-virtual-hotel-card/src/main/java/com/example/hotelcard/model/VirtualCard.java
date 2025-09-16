package com.example.hotelcard.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "virtual_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VirtualCard {

    @Id
    private String sessionId;  // Primary key

    private String userId;

    private LocalDateTime validFrom;
    private LocalDateTime validTill;

    @ElementCollection
    @CollectionTable(name = "card_amenities", joinColumns = @JoinColumn(name = "session_id"))
    @Column(name = "amenity")
    private List<String> amenitiesAllowed;
}
