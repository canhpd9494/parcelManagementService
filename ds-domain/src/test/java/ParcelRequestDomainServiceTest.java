import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.guestsmanagement.guest.GuestRepository;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestCreateCommand;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestDomainService;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelRepository;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParcelRequestDomainServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private ParcelRequestDomainService domainService;

    @Captor
    private ArgumentCaptor<Parcel> parcelCaptor;

    private Guest guest;
    private Parcel parcel;
    private ParcelRequestCreateCommand command;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 4, 20);

        // Setup Guest
        guest = new Guest();
        guest.setGuestId(1);

        // Setup Parcel
        parcel = new Parcel();
        parcel.setParcelId(1);
        parcel.setGuest(guest);
        parcel.setReceivedDate(testDate);
        parcel.setStatus(ParcelStatus.PENDING.getCode());
        parcel.setDescription("Test package");

        // Setup command
        command = new ParcelRequestCreateCommand(1, testDate, "Test package");

        // Setup mocks for common behaviors
        when(guestRepository.findByGuestId(eq(1))).thenReturn(Collections.singletonList(guest));
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel);
        when(parcelRepository.findByParcelId(eq(1))).thenReturn(Collections.singletonList(parcel));
    }

    @Test
    void testFindParcelsByParcelId() {
        // Arrange
        Integer parcelId = 1;

        // Act
        List<Parcel> result = domainService.findParcels(parcelId, null, null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(parcelId, result.get(0).getParcelId());
        verify(parcelRepository).findByParcelId(parcelId);
        verify(parcelRepository, never()).findByAllField(any(), any(), any(), any());
    }

    @Test
    void testFindParcelsByAllFields() {
        // Arrange
        Integer guestId = 1;
        String status = "P";
        when(parcelRepository.findByAllField(eq(guestId), eq(testDate), eq(null), eq(status)))
                .thenReturn(Collections.singletonList(parcel));

        // Act
        List<Parcel> result = domainService.findParcels(null, guestId, testDate, null, status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(guestId, result.get(0).getGuest().getGuestId());
        verify(parcelRepository, never()).findByParcelId(any());
        verify(parcelRepository).findByAllField(guestId, testDate, null, status);
    }

    @Test
    void testCreateParcel() {
        // Act
        Parcel result = domainService.createParcel(command);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getParcelId());
        assertEquals(1, result.getGuest().getGuestId());
        assertEquals(testDate, result.getReceivedDate());
        assertEquals(ParcelStatus.PENDING.getCode(), result.getStatus());
        assertEquals("Test package", result.getDescription());

        verify(guestRepository).findByGuestId(1);
        verify(parcelRepository).save(any(Parcel.class));
    }

    @Test
    void testPickupParcel() {
        // Arrange
        Integer parcelId = 1;
        LocalDate today = LocalDate.now();

        // Act
        domainService.pickupParcel(parcelId);

        // Assert
        verify(parcelRepository).findByParcelId(parcelId);
        verify(parcelRepository).save(parcelCaptor.capture());

        Parcel savedParcel = parcelCaptor.getValue();
        assertEquals(ParcelStatus.COMPLETED.getCode(), savedParcel.getStatus());
        // Using LocalDate.now() in test is tricky, so we just verify it's been set
        assertNotNull(savedParcel.getPickupDate());
    }
}