import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateResponseDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelRequestApplicationService;
import com.service.parcelmanagement.rest.parcelmanagement.ParcelRequestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParcelRequestControllerTest {

    @Mock
    private ParcelRequestApplicationService applicationService;

    @InjectMocks
    private ParcelRequestController controller;

    private ParcelCreateDto createDto;
    private ParcelCreateResponseDto responseDto;
    private List<ParcelQueryDto> parcelList;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 4, 20);

        // Setup ParcelCreateDto
        createDto = new ParcelCreateDto();
        createDto.setGuestId(1);
        createDto.setReceivedDate(testDate);
        createDto.setDescription("Test package");

        // Setup ParcelCreateResponseDto
        responseDto = new ParcelCreateResponseDto(1, 1, testDate, "PENDING", "Test package");

        // Setup ParcelQueryDto list
        parcelList = new ArrayList<>();
        ParcelQueryDto queryDto = new ParcelQueryDto();
        queryDto.setId(1);
        queryDto.setGuestId(1);
        queryDto.setReceivedDate(testDate);
        queryDto.setStatus("PENDING");
        queryDto.setDescription("Test package");
        parcelList.add(queryDto);
    }

    @Test
    void testCreateParcel() {
        // Arrange
        when(applicationService.createParcel(any(ParcelCreateDto.class))).thenReturn(responseDto);

        // Act
        ResponseEntity<?> response = controller.createParcel(createDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(applicationService).createParcel(createDto);
    }

    @Test
    void testFindParcels() {
        // Arrange
        Integer id = 1;
        Integer guestId = 1;
        String status = "PENDING";
        when(applicationService.findParcels(eq(id), eq(guestId), eq(testDate), eq(null), eq(status)))
                .thenReturn(parcelList);

        // Act
        List<ParcelQueryDto> result = controller.findParcels(id, guestId, testDate, null, status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
        assertEquals(guestId, result.get(0).getGuestId());
        assertEquals(testDate, result.get(0).getReceivedDate());
        assertEquals(status, result.get(0).getStatus());
        verify(applicationService).findParcels(id, guestId, testDate, null, status);
    }

    @Test
    void testPickupParcel() {
        // Arrange
        Integer parcelId = 1;

        // Act
        ResponseEntity<?> response = controller.pickupParcel(parcelId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNotNull(response.getHeaders().get("X-ID"));
        assertEquals(String.valueOf(parcelId), response.getHeaders().get("X-ID").get(0));
        verify(applicationService).pickupParcel(parcelId);
    }
}