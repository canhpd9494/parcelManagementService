
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.service.parcelmanagement.app.guestmanagement.GuestQueryDto;
import com.service.parcelmanagement.app.guestmanagement.GuestRequestApplicationService;
import com.service.parcelmanagement.app.guestmanagement.GuestSearchDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.rest.guestmanagement.GuestRequestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GuestRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GuestRequestApplicationService applicationService;

    @InjectMocks
    private GuestRequestController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // For handling LocalDate
    }

    @Test
    void findGuests_ShouldReturnGuestList() throws Exception {
        // Arrange
        GuestSearchDto searchDto = new GuestSearchDto();
        searchDto.setName("John");
        searchDto.setRoomNumber(101);

        List<GuestQueryDto> expectedGuests = Arrays.asList(
                createGuestQueryDto(1, "John Doe", "MALE", "123456789", "john@example.com", 12345678901L, 101, "CHECKED_IN"),
                createGuestQueryDto(2, "Johnny Smith", "MALE", "987654321", "johnny@example.com", 98765432109L, 102, "CHECKED_IN")
        );

        when(applicationService.findGuests(any(GuestSearchDto.class))).thenReturn(expectedGuests);

        // Act & Assert
        mockMvc.perform(get("/api/find-guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedGuests)));
    }

    @Test
    void getPendingParcels_ShouldReturnParcelList() throws Exception {
        // Arrange
        Integer guestId = 1;
        List<ParcelQueryDto> expectedParcels = Arrays.asList(
                createParcelQueryDto(1, guestId, LocalDate.now(), null, "PENDING", "Package from Amazon"),
                createParcelQueryDto(2, guestId, LocalDate.now().minusDays(1), null, "PENDING", "Mail delivery")
        );

        when(applicationService.getPendingParcels(eq(guestId))).thenReturn(expectedParcels);

        // Act & Assert
        mockMvc.perform(get("/api/guests/{guestId}/pending-parcels", guestId)
                        .param("id", guestId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedParcels)));
    }

    // Helper methods with all fields from the actual DTO
    private GuestQueryDto createGuestQueryDto(Integer id, String name, String gender, String phoneNumber,
                                              String email, Long socialId, Integer roomNumber, String status) {
        GuestQueryDto dto = new GuestQueryDto();
        dto.setId(id);
        dto.setName(name);
        dto.setGender(gender);
        dto.setPhoneNumber(phoneNumber);
        dto.setEmail(email);
        dto.setSocialId(socialId);
        dto.setRoomNumber(roomNumber);
        dto.setStatus(status);
        return dto;
    }

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