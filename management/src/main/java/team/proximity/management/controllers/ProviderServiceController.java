package team.proximity.management.controllers;

import com.amazonaws.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import team.proximity.management.dtos.ProviderServiceDTO;
import team.proximity.management.requests.ProviderServiceRequest;
import team.proximity.management.model.ProviderService;
import team.proximity.management.responses.ApiSuccessResponse;
import team.proximity.management.responses.ApiResponseStatus;
import team.proximity.management.responses.ErrorResponse;
import team.proximity.management.services.ProviderServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/provider-services")
public class ProviderServiceController {
    private final ProviderServiceService providerServiceService;

    public ProviderServiceController(ProviderServiceService providerServiceService) {
        this.providerServiceService = providerServiceService;
    }
    @PostMapping(consumes = "multipart/form-data")

    public ResponseEntity<ApiSuccessResponse<ProviderService>> createOrUpdateProviderService(@Validated @ModelAttribute ProviderServiceRequest providerServiceRequest) throws JsonProcessingException {
        log.info("Processing providerService request: {}", providerServiceRequest);

        ProviderService providerService = providerServiceService.createOrUpdateProviderService(providerServiceRequest);

        log.debug("Processed providerService: {}", providerService);
        ApiSuccessResponse<ProviderService> response = ApiSuccessResponse.<ProviderService>builder()
                .status(ApiResponseStatus.SUCCESS)
                .result(providerService)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/email")
    public ResponseEntity<List<ProviderServiceDTO>> getProviderServiceByUserEmail(@RequestParam String userEmail) {
        log.info("Fetching providerService with userId: {}", userEmail);

        List<ProviderServiceDTO> providerServices = providerServiceService.getProviderServicesByUserEmail(userEmail);

        log.debug("Fetched providerService: {}", providerServices);
        return ResponseEntity.ok(providerServices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ProviderService>> getProviderServiceById(@PathVariable UUID id) {
        log.info("Fetching providerService with id: {}", id);

        ProviderService providerService = providerServiceService.getProviderServiceById(id);

        ApiSuccessResponse<ProviderService> response = ApiSuccessResponse.<ProviderService>builder()
                .status(ApiResponseStatus.SUCCESS)
                .result(providerService)
                .build();

        log.debug("Fetched providerService: {}", providerService);
        return ResponseEntity.ok(response);
    }

    @GetMapping

    public ResponseEntity<ApiSuccessResponse<Page<ProviderService>>> getAllProviderServices(Pageable pageable) {
        log.info("Fetching all providerServices");
        Page<ProviderService> providerServices = providerServiceService.getAllProviderServices(pageable);
        log.debug("Fetched providerServices: {}", providerServices);
        ApiSuccessResponse<Page<ProviderService>> response = ApiSuccessResponse.<Page<ProviderService>>builder()
                .status(ApiResponseStatus.SUCCESS)
                .result(providerServices)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteProviderService(@PathVariable UUID id) {
        log.info("Deleting providerService with id: {}", id);
        providerServiceService.deleteProviderService(id);
        ApiSuccessResponse<Void> response = ApiSuccessResponse.<Void>builder()
                .status(ApiResponseStatus.SUCCESS)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}