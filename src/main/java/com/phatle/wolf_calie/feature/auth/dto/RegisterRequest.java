package com.phatle.wolf_calie.feature.auth.dto;

import com.phatle.wolf_calie.feature.user.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank(message = "Tên không được để trống")
        @Size(min = 2, max = 100, message = "Tên phải từ 2 đến 100 ký tự")
        String name,

        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không đúng định dạng")
        String email,

        @NotBlank(message = "Mật khẩu không được để trống")
        @Size(min = 8, max = 100, message = "Mật khẩu phải có ít nhất 8 ký tự")
        String password,

        @Size(max = 20, message = "Số điện thoại không hợp lệ")
        String phone,

        Gender gender,

        @Past(message = "Ngày sinh không hợp lệ")
        LocalDate birthday
) {
}
