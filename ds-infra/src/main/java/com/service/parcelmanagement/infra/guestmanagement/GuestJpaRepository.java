package com.service.parcelmanagement.infra.guestmanagement;

import com.service.parcelmanagement.domain.guestsmanagement.guest.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestJpaRepository extends JpaRepository<Guest, Integer> {

    @Query(value = "SELECT g.* FROM guests g " +
            "WHERE (:name IS NULL OR g.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:gender IS NULL OR g.gender = :gender) " +
            "AND (:roomNumber IS NULL OR g.room_number = :roomNumber) " +
            "AND (:phoneNumber IS NULL OR g.phone_number = :phoneNumber) " +
            "AND (:email IS NULL OR g.email = :email) " +
            "AND (:socialId IS NULL OR g.social_id = :socialId) " +
            "AND (:status IS NULL OR g.status = :status) " +
            "ORDER BY g.guest_id DESC", nativeQuery = true)
    List<Guest> findByAllField(String name, String gender, Integer roomNumber, String phoneNumber, String email, Long socialId, String status);
}
