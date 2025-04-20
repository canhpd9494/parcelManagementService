package com.service.parcelmanagement.domain.guestsmanagement.guest;

public enum GuestStatus {
    BOOKING("B"),
    CHECKIN("I"),
    CHECKOUT("O");

    private final String code;

    GuestStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static GuestStatus fromCode(String code) {
        for (GuestStatus status : GuestStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No GuestStatus with code: " + code);
    }
}
