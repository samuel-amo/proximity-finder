package team.proximity.request_management.request_management.booking;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;
    private String startDate;
    private String startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private String description;
    private String createdBy;
    private String assignedProvider;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
