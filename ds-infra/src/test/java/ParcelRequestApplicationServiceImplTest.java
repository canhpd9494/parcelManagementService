import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateResponseDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelRequestMapper;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestCreateCommand;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestDomainService;
import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.infra.parcelmanagement.ParcelRequestApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParcelRequestApplicationServiceImplTest {

    @Mock
    private ParcelRequestDomainService domainService;

    @Mock
    private ParcelRequestMapper mapper;

    @InjectMocks
    private ParcelRequestApplicationServiceImpl applicationService;

    private ParcelCreateDto createDto;
    private Parcel parcel;
    private ParcelCreateResponseDto responseDto;
    private ParcelRequestCreateCommand command;
    private List<Parcel> parcelList;
    private List<ParcelQueryDto> parcelQueryDtoList;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 4, 20);

        // Setup ParcelCreateDto
        createDto = new ParcelCreateDto();
        createDto.setGuestId(1);
        createDto.setReceivedDate(testDate);
        createDto.setDescription("Test package");

        // Setup Parcel
        parcel = new Parcel();
        parcel.setParcelId(1);
        Guest guest = new Guest();
        guest.setGuestId(1);
        parcel.setGuest(guest);
        parcel.setReceivedDate(testDate);
        parcel.setStatus("P");
        parcel.setDescription("Test package");

        // Setup command
        command = new ParcelRequestCreateCommand(1, testDate, "Test package");

        // Setup ResponseDto
        responseDto = new ParcelCreateResponseDto(1, 1, testDate, "PENDING", "Test package");

        // Setup Lists
        parcelList = new ArrayList<>();
        parcelList.add(parcel);

        parcelQueryDtoList = new ArrayList<>();
        ParcelQueryDto queryDto = new ParcelQueryDto();
        queryDto.setId(1);
        queryDto.setGuestId(1);
        queryDto.setReceivedDate(testDate);
        queryDto.setStatus("PENDING");
        queryDto.setDescription("Test package");
        parcelQueryDtoList.add(queryDto);
    }

    @Test
    void testFindParcels() {
        // Arrange
        Integer id = 1;
        Integer guestId = 1;
        String status = "P";
        when(domainService.findParcels(eq(id), eq(guestId), eq(testDate), eq(null), eq(status)))
                .thenReturn(parcelList);
        when(mapper.map(parcelList)).thenReturn(parcelQueryDtoList);

        // Act
        List<ParcelQueryDto> result = applicationService.findParcels(id, guestId, testDate, null, status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
        verify(domainService).findParcels(id, guestId, testDate, null, status);
        verify(mapper).map(parcelList);
    }

    @Test
    void testCreateParcel() {
        // Arrange
        when(mapper.map(createDto)).thenReturn(command);
        when(domainService.createParcel(command)).thenReturn(parcel);
        when(mapper.map(parcel)).thenReturn(responseDto);

        // Act
        ParcelCreateResponseDto result = applicationService.createParcel(createDto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getParcelId());
        assertEquals(1, result.getGuestId());
        assertEquals(testDate, result.getReceivedDate());
        assertEquals("PENDING", result.getStatus());
        assertEquals("Test package", result.getDescription());
        verify(mapper).map(createDto);
        verify(domainService).createParcel(command);
        verify(mapper).map(parcel);
    }

    @Test
    void testPickupParcel() {
        // Arrange
        Integer parcelId = 1;

        // Act
        applicationService.pickupParcel(parcelId);

        // Assert
        verify(domainService).pickupParcel(parcelId);
    }
}