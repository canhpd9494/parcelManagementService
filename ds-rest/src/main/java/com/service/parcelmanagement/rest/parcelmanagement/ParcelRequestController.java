package com.service.parcelmanagement.rest.parcelmanagement;

import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateResponseDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelRequestApplicationService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ParcelRequestController {

    private final ParcelRequestApplicationService applicationService;

    public ParcelRequestController(ParcelRequestApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create-parcel")
    public ResponseEntity<?> createParcel(@Valid @RequestBody ParcelCreateDto parcel) {
        ParcelCreateResponseDto response = applicationService.createParcel(parcel);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/find-parcel")
    public List<ParcelQueryDto> findParcels(@RequestParam(value = "id", required = false) @Parameter(description = "Parcel Id") Integer id,
                                            @RequestParam(value = "guestId", required = false) @Parameter(description = "Guest Id") Integer guestId,
                                            @RequestParam(value = "receivedDate", required = false) @Parameter(description = "Receive Date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate receivedDate,
                                            @RequestParam(value = "pickupDate", required = false) @Parameter(description = "Pickup Date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickupDate,
                                            @RequestParam(value = "status", required = false) @Parameter(description = "Parcel Status") String status) {
        return applicationService.findParcels(id, guestId, receivedDate, pickupDate, status);
    }

    @PatchMapping("/parcels/{parcelId}/pickup")
    public ResponseEntity<?> pickupParcel(@PathVariable(value = "parcelId") @Parameter(description = "Parcel for pickup") Integer parcelId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-ID", String.valueOf(parcelId));
        applicationService.pickupParcel(parcelId);
        return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
    }
}
