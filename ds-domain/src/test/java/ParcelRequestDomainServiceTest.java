
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParcelRequestDomainServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private ParcelRequestDomainService domainService;

    @Captor
    private ArgumentCaptor<Parcel> parcelCaptor;

    private Guest testGuest;
    private Parcel testParcel;
    private LocalDate now;

    @BeforeEach
    public void setup() {
        now = LocalDate.now();

        // Setup test guest
        testGuest = new Guest();
        testGuest.setGuestId(1);
        testGuest.setName("Test Guest");
        testGuest.setGender("M");
        testGuest.setPhoneNumber("123456789");
        testGuest.setSocialId(123456789L);
        testGuest.setStatus("I"); // Checked-in

        // Setup test parcel
        testParcel = new Parcel();
        testParcel.setParcelId(1);
        testParcel.setGuest(testGuest);
        testParcel.setReceivedDate(now);
        testParcel.setStatus(ParcelStatus.PENDING.getCode());
        testParcel.setDescription("Test parcel");
    }

    @Test
    public void findParcels_ById_ShouldReturnMatchingParcel() {
        // Arrange
        when(parcelRepository.findByParcelId(1)).thenReturn(Collections.singletonList(testParcel));

        // Act
        List<Parcel> result = domainService.findParcels(1, null, null, null, null);

        // Assert
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getParcelId());
        verify(parcelRepository, times(1)).findByParcelId(1);
        verify(parcelRepository, never()).findByAllField(any(), any(), any(), any());
    }

    @Test
    public void findParcels_ByAttributes_ShouldReturnMatchingParcels() {
        // Arrange
        when(parcelRepository.findByAllField(1, now, null, "P"))
                .thenReturn(Collections.singletonList(testParcel));

        // Act
        List<Parcel> result = domainService.findParcels(null, 1, now, null, "P");

        // Assert
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getParcelId());
        verify(parcelRepository, never()).findByParcelId(any());
        verify(parcelRepository, times(1)).findByAllField(1, now, null, "P");
    }

    @Test
    public void createParcel_ShouldCreateAndReturnParcel() {
        // Arrange
        ParcelRequestCreateCommand command = new ParcelRequestCreateCommand(1, now, "Test parcel");
        when(guestRepository.findByGuestId(1)).thenReturn(Collections.singletonList(testGuest));
        when(parcelRepository.save(any(Parcel.class))).thenReturn(testParcel);

        // Act
        Parcel result = domainService.createParcel(command);

        // Assert
        verify(parcelRepository).save(parcelCaptor.capture());
        Parcel savedParcel = parcelCaptor.getValue();

        assertNotNull(result);
        assertEquals(testGuest, savedParcel.getGuest());
        assertEquals(now, savedParcel.getReceivedDate());
        assertEquals(ParcelStatus.PENDING.getCode(), savedParcel.getStatus());
        assertEquals("Test parcel", savedParcel.getDescription());
    }

    @Test
    public void pickupParcel_ShouldUpdateParcelStatusAndSetPickupDate() {
        // Arrange
        when(parcelRepository.findByParcelId(1)).thenReturn(Collections.singletonList(testParcel));

        // Act
        domainService.pickupParcel(1);

        // Assert
        verify(parcelRepository).save(parcelCaptor.capture());
        Parcel updatedParcel = parcelCaptor.getValue();

        assertEquals(ParcelStatus.COMPLETED.getCode(), updatedParcel.getStatus());
        assertNotNull(updatedParcel.getPickupDate());
    }
}