import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateResponseDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelRequestMapper;
import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestCreateCommand;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestDomainService;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelStatus;
import com.service.parcelmanagement.infra.parcelmanagement.ParcelRequestApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * This test class assumes you have a ParcelRequestApplicationServiceImpl class that implements
 * ParcelRequestApplicationService interface. Adjust the class name if needed.
 */
@ExtendWith(MockitoExtension.class)
public class ParcelRequestApplicationServiceImplTest {

    @Mock
    private ParcelRequestDomainService domainService;

    @Mock
    private ParcelRequestMapper mapper;

    // Replace with your actual implementation class name
    @InjectMocks
    private ParcelRequestApplicationServiceImpl applicationService;

    @Captor
    private ArgumentCaptor<ParcelRequestCreateCommand> commandCaptor;

    private ParcelCreateDto createDto;
    private Parcel parcel;
    private Guest guest;
    private LocalDate now;

    @BeforeEach
    public void setup() {
        now = LocalDate.now();

        // Setup test DTO
        createDto = new ParcelCreateDto();
        createDto.setGuestId(1);
        createDto.setReceivedDate(now);
        createDto.setDescription("Test parcel");

        // Setup test guest
        guest = new Guest();
        guest.setGuestId(1);

        // Setup test parcel
        parcel = new Parcel();
        parcel.setParcelId(1);
        parcel.setGuest(guest);
        parcel.setReceivedDate(now);
        parcel.setStatus(ParcelStatus.PENDING.getCode());
        parcel.setDescription("Test parcel");
    }

    @Test
    public void findParcels_ShouldReturnMappedParcels() {
        // Arrange
        List<Parcel> parcels = Collections.singletonList(parcel);
        ParcelQueryDto queryDto = new ParcelQueryDto();
        queryDto.setId(1);

        when(domainService.findParcels(1, 2, now, null, "P"))
                .thenReturn(parcels);
        when(mapper.map(parcels)).thenReturn(Collections.singletonList(queryDto));

        // Act
        List<ParcelQueryDto> result = applicationService.findParcels(1, 2, now, null, "P");

        // Assert
        assertEquals(1, result.size());
        verify(domainService).findParcels(1, 2, now, null, "P");
        verify(mapper).map(parcels);
    }

    @Test
    public void createParcel_ShouldMapDtoAndCallDomainService() {
        // Arrange
        ParcelRequestCreateCommand command = new ParcelRequestCreateCommand(1, now, "Test parcel");
        ParcelCreateResponseDto responseDto = new ParcelCreateResponseDto(1, 1, now, "PENDING", "Test parcel");

        when(mapper.map(createDto)).thenReturn(command);
        when(domainService.createParcel(any(ParcelRequestCreateCommand.class))).thenReturn(parcel);
        when(mapper.map(parcel)).thenReturn(responseDto);

        // Act
        ParcelCreateResponseDto result = applicationService.createParcel(createDto);

        // Assert
        verify(mapper).map(createDto);
        verify(domainService).createParcel(commandCaptor.capture());
        verify(mapper).map(parcel);

        ParcelRequestCreateCommand capturedCommand = commandCaptor.getValue();
        assertEquals(1, capturedCommand.getGuestId());
        assertEquals(now, capturedCommand.getReceivedDate());
        assertEquals("Test parcel", capturedCommand.getDescription());

        assertEquals(1, result.getParcelId());
        assertEquals(1, result.getGuestId());
        assertEquals(now, result.getReceivedDate());
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    public void pickupParcel_ShouldCallDomainService() {
        // Act
        applicationService.pickupParcel(1);

        // Assert
        verify(domainService).pickupParcel(1);
    }
}