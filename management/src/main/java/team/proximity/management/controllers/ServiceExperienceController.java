package team.proximity.management.controllers;



import team.proximity.management.model.ServiceExperience;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.proximity.management.repositories.ServiceExperienceRepository;
import team.proximity.management.requests.ServiceExperienceRequest;
import team.proximity.management.services.ServiceExperienceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/service-experiences")
public class ServiceExperienceController {

    private final ServiceExperienceService experienceService;

    @Autowired
    public ServiceExperienceController(ServiceExperienceService experienceService, ServiceExperienceRepository serviceExperienceRepository) {
        this.experienceService = experienceService;
    }

    @GetMapping

    public List<ServiceExperience> getAllServiceExperiences() {
        return experienceService.getAllServiceExperiences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceExperience> getServiceExperienceById(@PathVariable Long id) {
        return experienceService.getServiceExperienceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ServiceExperience createServiceExperience( @ModelAttribute ServiceExperienceRequest serviceExperienceRequest) {
        return experienceService.createServiceExperience(serviceExperienceRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ServiceExperience>> updateServiceExperience(
            @PathVariable Long id,
            @RequestBody ServiceExperienceRequest serviceExperienceRequest) {
        return experienceService.getServiceExperienceById(id)
                .map(existing -> ResponseEntity.ok(experienceService.updateServiceExperience(id, serviceExperienceRequest)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceExperience(@PathVariable Long id) {
        experienceService.deleteServiceExperience(id);
        return ResponseEntity.noContent().build();
    }
}
