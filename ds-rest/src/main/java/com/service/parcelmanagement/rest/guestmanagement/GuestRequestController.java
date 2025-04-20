package com.service.parcelmanagement.rest.guestmanagement;

import com.service.parcelmanagement.app.guestmanagement.GuestQueryDto;
import com.service.parcelmanagement.app.guestmanagement.GuestRequestApplicationService;
import com.service.parcelmanagement.app.guestmanagement.GuestSearchDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestRequestController {

    private final GuestRequestApplicationService applicationService;

    public GuestRequestController(GuestRequestApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/find-guests")
    public List<GuestQueryDto> findGuests(@RequestBody GuestSearchDto dto) {
        return applicationService.findGuests(dto);
    }

    @GetMapping("/guests/{guestId}/pending-parcels")
    public List<ParcelQueryDto> getPendingParcels(@Validated @RequestParam @Parameter Integer id) {
        return applicationService.getPendingParcels(id);
    }
}
