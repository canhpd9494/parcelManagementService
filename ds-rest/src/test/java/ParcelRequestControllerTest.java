
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateResponseDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelRequestApplicationService;
import com.service.parcelmanagement.rest.parcelmanagement.ParcelRequestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ParcelRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParcelRequestApplicationService applicationService;

    @InjectMocks
    private ParcelRequestController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // For LocalDate serialization
    }

    @Test
    void createParcel_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        ParcelCreateDto createDto = new ParcelCreateDto();
        createDto.setGuestId(1);
        createDto.setReceivedDate(LocalDate.now());
        createDto.setDescription("Test package");

        ParcelCreateResponseDto responseDto = new ParcelCreateResponseDto(
                1, 1, LocalDate.now(), "PENDING", "Test package"
        );

        when(applicationService.createParcel(any(ParcelCreateDto.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/create-parcel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void findParcels_WithAllParameters_ShouldReturnParcelList() throws Exception {
        // Arrange
        Integer id = 1;
        Integer guestId = 2;
        LocalDate receivedDate = LocalDate.of(2025, 4, 1);
        LocalDate pickupDate = LocalDate.of(2025, 4, 15);
        String status = "PENDING";

        List<ParcelQueryDto> expectedParcels = Arrays.asList(
                createParcelQueryDto(1, guestId, receivedDate, null, "PENDING", "Package from Amazon"),
                createParcelQueryDto(2, guestId, receivedDate, null, "PENDING", "Mail delivery")
        );

        when(applicationService.findParcels(eq(id), eq(guestId), eq(receivedDate), eq(pickupDate), eq(status)))
                .thenReturn(expectedParcels);

        // Act & Assert
        mockMvc.perform(get("/api/find-parcel")
                        .param("id", id.toString())
                        .param("guestId", guestId.toString())
                        .param("receivedDate", receivedDate.toString())
                        .param("pickupDate", pickupDate.toString())
                        .param("status", status))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedParcels)));
    }

    @Test
    void findParcels_WithNoParameters_ShouldReturnAllParcels() throws Exception {
        // Arrange
        List<ParcelQueryDto> expectedParcels = Arrays.asList(
                createParcelQueryDto(1, 1, LocalDate.now(), null, "PENDING", "Package 1"),
                createParcelQueryDto(2, 2, LocalDate.now().minusDays(1), LocalDate.now(), "DELIVERED", "Package 2")
        );

        when(applicationService.findParcels(null, null, null, null, null))
                .thenReturn(expectedParcels);

        // Act & Assert
        mockMvc.perform(get("/api/find-parcel"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedParcels)));
    }

    @Test
    void pickupParcel_ShouldReturnNoContent() throws Exception {
        // Arrange
        Integer parcelId = 1;
        doNothing().when(applicationService).pickupParcel(eq(parcelId));

        // Act & Assert
        mockMvc.perform(patch("/api/parcels/{parcelId}/pickup", parcelId))
                .andExpect(status().isNoContent())
                .andExpect(header().string("X-ID", parcelId.toString()));
    }

    // Helper method with all fields from the actual DTO
    private ParcelQueryDto createParcelQueryDto(Integer id, Integer guestId, LocalDate receivedDate,
                                                LocalDate pickupDate, String status, String description) {
        ParcelQueryDto dto = new ParcelQueryDto();
        dto.setId(id);
        dto.setGuestId(guestId);
        dto.setReceivedDate(receivedDate);
        dto.setPickupDate(pickupDate);
        dto.setStatus(status);
        dto.setDescription(description);
        return dto;
    }
}