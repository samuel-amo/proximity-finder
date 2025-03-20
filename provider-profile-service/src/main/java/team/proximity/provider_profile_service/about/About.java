package team.proximity.provider_profile_service.about;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDate inceptionDate;
    @ElementCollection
    private Set<String> socialMediaLinks;
    private Integer numberOfEmployees;
    private String businessIdentityCard;
    private String businessCertificate;
    private String businessSummary;
    private String createdBy;
}
