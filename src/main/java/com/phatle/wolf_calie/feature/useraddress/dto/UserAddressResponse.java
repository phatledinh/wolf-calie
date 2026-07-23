package com.phatle.wolf_calie.feature.useraddress.dto;

import com.phatle.wolf_calie.feature.useraddress.UserAddress;

import java.time.Instant;

public record UserAddressResponse(
        long id,
        String receiverName,
        String receiverPhone,
        String provinceId,
        String districtId,
        String wardId,
        String street,
        boolean isDefault,
        Instant createdAt
) {
    public static UserAddressResponse fromEntity(UserAddress address) {
        return new UserAddressResponse(
                address.getId(),
                address.getReceiverName(),
                address.getReceiverPhone(),
                address.getProvinceId(),
                address.getDistrictId(),
                address.getWardId(),
                address.getStreet(),
                address.isDefault(),
                address.getCreatedAt()
        );
    }
}
