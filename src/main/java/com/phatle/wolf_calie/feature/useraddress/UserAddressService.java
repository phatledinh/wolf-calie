package com.phatle.wolf_calie.feature.useraddress;

import com.phatle.wolf_calie.feature.useraddress.dto.CreateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UpdateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UserAddressResponse;

import java.util.List;

public interface UserAddressService {

    List<UserAddressResponse> getUserAddresses(long userId);

    UserAddressResponse createUserAddress(long userId, CreateUserAddressRequest request);

    UserAddressResponse updateUserAddress(long id, long userId, UpdateUserAddressRequest request);

    void deleteUserAddress(long id, long userId);
}
