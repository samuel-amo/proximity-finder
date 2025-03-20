package team.proximity.request_management.request_management.booking;

import org.springframework.stereotype.Component;
import team.proximity.request_management.request_management.security.SecurityContextUtils;
@Component
public class BookingMapper {


    public Booking toBooking(BookingRequest request) {
        return Booking.builder()
                .startDate(request.startDate())
                .startTime(request.startTime())
                .endDate(request.endDate())
                .endTime(request.endTime())
                .description(request.description())
                .assignedProvider(request.assignedProvider())
                .status(BookingStatus.PENDING)
                .createdBy(SecurityContextUtils.getEmail())
                .build();
    }


    public BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingId(),
                booking.getStartDate(),
                booking.getStartTime(),
                booking.getEndDate().toString(),
                booking.getEndTime().toString(),
                booking.getDescription(),
                booking.getCreatedBy(),
                booking.getAssignedProvider(),
                booking.getStatus().toString()

        );
    }

}
