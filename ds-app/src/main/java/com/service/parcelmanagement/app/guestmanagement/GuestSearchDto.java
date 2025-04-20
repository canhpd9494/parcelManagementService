package com.service.parcelmanagement.app.guestmanagement;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuestSearchDto")
public class GuestSearchDto {
    @Schema(description = "Guest Id")
    private Integer id;

    @Schema(description = "Guest Name")
    private String name;

    @Schema(description = "Guest Gender")
    private String gender;

    @Schema(description = "Guest Phone Number")
    private String phoneNumber;

    @Schema(description = "Guest Email")
    private String email;

    @Schema(description = "Guest Social Id")
    private Long socialId;

    @Schema(description = "Guest Room Number")
    private Integer roomNumber;

    @Schema(description = "Guest Checking Status")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSocialId() {
        return socialId;
    }

    public void setSocialId(Long socialId) {
        this.socialId = socialId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
