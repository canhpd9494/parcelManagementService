import com.service.parcelmanagement.domain.parcelmanagement.parcel.Parcel;
import com.service.parcelmanagement.infra.parcelmanagement.ParcelJpaRepository;
import com.service.parcelmanagement.infra.parcelmanagement.ParcelRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParcelRepositoryImplTest {

    @Mock
    private ParcelJpaRepository jpaRepository;

    @InjectMocks
    private ParcelRepositoryImpl repository;

    private Parcel parcel;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 4, 20);

        // Setup Parcel
        parcel = new Parcel();
        parcel.setParcelId(1);
        parcel.setReceivedDate(testDate);
        parcel.setStatus("P");
        parcel.setDescription("Test package");
    }

    @Test
    void testFindByParcelIdWhenExists() {
        // Arrange
        when(jpaRepository.findById(eq(1))).thenReturn(Optional.of(parcel));

        // Act
        List<Parcel> result = repository.findByParcelId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getParcelId());
        verify(jpaRepository).findById(1);
    }

    @Test
    void testFindByParcelIdWhenNotExists() {
        // Arrange
        when(jpaRepository.findById(eq(999))).thenReturn(Optional.empty());

        // Act
        List<Parcel> result = repository.findByParcelId(999);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getParcelId());  // Empty parcel is returned
        verify(jpaRepository).findById(999);
    }

    @Test
    void testFindByAllField() {
        // Arrange
        Integer guestId = 1;
        String status = "P";
        when(jpaRepository.findByAllField(eq(guestId), eq(testDate), eq(null), eq(status)))
                .thenReturn(Collections.singletonList(parcel));

        // Act
        List<Parcel> result = repository.findByAllField(guestId, testDate, null, status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getParcelId());
        verify(jpaRepository).findByAllField(guestId, testDate, null, status);
    }

    @Test
    void testSave() {
        // Arrange
        when(jpaRepository.save(any(Parcel.class))).thenReturn(parcel);

        // Act
        Parcel result = repository.save(parcel);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getParcelId());
        verify(jpaRepository).save(parcel);
    }
}