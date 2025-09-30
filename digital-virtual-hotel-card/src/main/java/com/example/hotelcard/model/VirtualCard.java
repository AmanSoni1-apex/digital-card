package com.example.hotelcard.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import javax.print.DocFlavor.STRING;

@Entity
@Table(name = "virtual_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VirtualCard {

  @Id
    private String sessionId;

    private String userId;
    private String guestName;
    private LocalDateTime validFrom;
    private LocalDateTime validTill;

    // @ElementCollection
    // @CollectionTable(name = "card_amenities", joinColumns = @JoinColumn(name = "session_id"))
    // @Column(name = "amenity")
    private List<String> amenitiesAllowed;

    @Column(nullable = true)
    private Boolean suspended = false;

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getGuestName(){
        return guestName;
    }

    public void setGuestName(String guestName)
    {
        this.guestName=guestName;
    }
    
}
