package com.phatle.wolf_calie.feature.useraddress;

import com.phatle.wolf_calie.dto.ApiResponse;
import com.phatle.wolf_calie.feature.useraddress.dto.CreateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UpdateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UserAddressResponse;
import com.phatle.wolf_calie.security.AuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/me/addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;
    private final AuthenticationFacade authenticationFacade;

    public UserAddressController(UserAddressService userAddressService, AuthenticationFacade authenticationFacade) {
        this.userAddressService = userAddressService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserAddressResponse>>> getUserAddresses() {
        long userId = authenticationFacade.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(userAddressService.getUserAddresses(userId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserAddressResponse>> createUserAddress(
            @RequestBody @Valid CreateUserAddressRequest request) {
        long userId = authenticationFacade.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(userAddressService.createUserAddress(userId, request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserAddressResponse>> updateUserAddress(
            @PathVariable long id,
            @RequestBody @Valid UpdateUserAddressRequest request) {
        long userId = authenticationFacade.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success(userAddressService.updateUserAddress(id, userId, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserAddress(
            @PathVariable long id) {
        long userId = authenticationFacade.getCurrentUserId();
        userAddressService.deleteUserAddress(id, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
