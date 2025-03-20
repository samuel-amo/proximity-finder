package team.proximity.management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.proximity.management.exceptions.ResourceNotFoundException;
import team.proximity.management.model.Services;
import team.proximity.management.requests.ServiceRequest;
import team.proximity.management.requests.UpdateServiceRequest;
import team.proximity.management.responses.ApiSuccessResponse;
import team.proximity.management.responses.ApiResponseStatus;
import team.proximity.management.services.ServicesService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/services")
public class ServicesController {
    private final ServicesService servicesService;

    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<Services>>> getAllServices() {
        log.info("Fetching all services");
        List<Services> servicesList = servicesService.getAllServices();
        return ResponseEntity.ok(ApiSuccessResponse.<List<Services>>builder()
                .status(ApiResponseStatus.SUCCESS)
                .result(servicesList)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Services>> getServiceById(@PathVariable UUID id) {
        log.info("Fetching service with id: {}", id);
        Services service = servicesService.getServiceById(id);
        return ResponseEntity.ok(ApiSuccessResponse.<Services>builder()
                .status(ApiResponseStatus.SUCCESS)
                .result(service)
                .build());
    }


    @PostMapping
    public ResponseEntity<ApiSuccessResponse<Services>> createService(@Validated @ModelAttribute ServiceRequest serviceRequest) throws IOException {
        log.info("Creating new service with request: {}", serviceRequest);
        Services createdService = servicesService.createService(serviceRequest);
        log.debug("Created service: {}", createdService);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiSuccessResponse.<Services>builder()
                .status(ApiResponseStatus.SUCCESS)
                .result(createdService)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Services>> updateService(@PathVariable UUID id, @ModelAttribute UpdateServiceRequest serviceDetails) {
        log.info("Updating service with id: {} and request: {}", id, serviceDetails);
        Services updatedService = servicesService.updateService(id, serviceDetails);
        return ResponseEntity.ok(ApiSuccessResponse.<Services>builder()
                .status(ApiResponseStatus.SUCCESS)
                .result(updatedService)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteService(@PathVariable UUID id) {
        log.info("Deleting service with id: {}", id);
        servicesService.deleteService(id);
        log.debug("Deleted service with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiSuccessResponse.<Void>builder()
                .status(ApiResponseStatus.SUCCESS)
                .build());
    }
}