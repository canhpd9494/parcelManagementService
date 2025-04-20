import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelCreateResponseDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelQueryDto;
import com.service.parcelmanagement.app.parcelmanagement.ParcelRequestMapper;
import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import com.service.parcelmanagement.domain.parcelmanagement.ParcelRequestCreateCommand;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.domain.parcelmanagement.parcel.ParcelStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParcelRequestMapperTest {

    private ParcelRequestMapper mapper;
    private ParcelCreateDto createDto;
    private Parcel parcel;
    private LocalDate testDate;
    private Guest guest;

    @BeforeEach
    void setUp() {
        mapper = new ParcelRequestMapper();
        testDate = LocalDate.of(2025, 4, 20);

        // Setup ParcelCreateDto
        createDto = new ParcelCreateDto();
        createDto.setGuestId(1);
        createDto.setReceivedDate(testDate);
        createDto.setDescription("Test package");

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
    }

    @Test
    void testMapParcelCreateDtoToCommand() {
        // Act
        ParcelRequestCreateCommand command = mapper.map(createDto);

        // Assert
        assertNotNull(command);
        assertEquals(1, command.getGuestId());
        assertEquals(testDate, command.getReceivedDate());
        assertEquals("Test package", command.getDescription());
    }

    @Test
    void testMapParcelToParcelCreateResponseDto() {
        // Act
        ParcelCreateResponseDto responseDto = mapper.map(parcel);

        // Assert
        assertNotNull(responseDto);
        assertEquals(1, responseDto.getParcelId());
        assertEquals(1, responseDto.getGuestId());
        assertEquals(testDate, responseDto.getReceivedDate());
        assertEquals("PENDING", responseDto.getStatus());
        assertEquals("Test package", responseDto.getDescription());
    }

    @Test
    void testMapParcelListToParcelQueryDtoList() {
        // Act
        List<ParcelQueryDto> dtoList = mapper.map(Collections.singletonList(parcel));

        // Assert
        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());

        ParcelQueryDto dto = dtoList.get(0);
        assertEquals(1, dto.getId());
        assertEquals(1, dto.getGuestId());
        assertEquals(testDate, dto.getReceivedDate());
        assertEquals("PENDING", dto.getStatus());
        assertEquals("Test package", dto.getDescription());
    }

    @Test
    void testMapEmptyListWhenParcelHasNullId() {
        // Arrange
        Parcel emptyParcel = new Parcel();  // parcelId will be null

        // Act
        List<ParcelQueryDto> dtoList = mapper.map(Collections.singletonList(emptyParcel));

        // Assert
        assertNotNull(dtoList);
        assertTrue(dtoList.isEmpty());
    }

    @Test
    void testMapParcelWithNullStatus() {
        // Arrange
        parcel.setStatus(null);

        // Act
        List<ParcelQueryDto> dtoList = mapper.map(Collections.singletonList(parcel));

        // Assert
        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
        assertNull(dtoList.get(0).getStatus());
    }
}