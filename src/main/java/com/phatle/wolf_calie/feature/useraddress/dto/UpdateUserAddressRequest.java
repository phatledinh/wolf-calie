package com.phatle.wolf_calie.feature.useraddress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserAddressRequest(
        @NotBlank(message = "Receiver name is required")
        @Size(max = 100, message = "Receiver name must not exceed 100 characters")
        String receiverName,

        @NotBlank(message = "Receiver phone is required")
        @Size(max = 20, message = "Receiver phone must not exceed 20 characters")
        String receiverPhone,

        @NotBlank(message = "Province ID is required")
        @Size(max = 50, message = "Province ID must not exceed 50 characters")
        String provinceId,

        @NotBlank(message = "District ID is required")
        @Size(max = 50, message = "District ID must not exceed 50 characters")
        String districtId,

        @NotBlank(message = "Ward ID is required")
        @Size(max = 50, message = "Ward ID must not exceed 50 characters")
        String wardId,

        @NotBlank(message = "Street address is required")
        @Size(max = 255, message = "Street address must not exceed 255 characters")
        String street,

        @NotNull(message = "isDefault status is required")
        Boolean isDefault
) {
}
