package com.service.parcelmanagement.domain.parcelmanagement.parcel;

public enum ParcelStatus {
    PENDING("P"),
    COMPLETED("C");

    private final String code;

    ParcelStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ParcelStatus fromCode(String code) {
        for (ParcelStatus status : ParcelStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No ParcelStatus with code: " + code);
    }
}
