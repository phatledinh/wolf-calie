package com.phatle.wolf_calie.feature.useraddress;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.user.User;
import com.phatle.wolf_calie.feature.user.UserRepository;
import com.phatle.wolf_calie.feature.useraddress.dto.CreateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UpdateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UserAddressResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    public UserAddressServiceImpl(UserAddressRepository userAddressRepository, UserRepository userRepository) {
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAddressResponse> getUserAddresses(long userId) {
        return userAddressRepository.findByUserIdAndDeletedAtIsNull(userId).stream()
                .map(UserAddressResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserAddressResponse createUserAddress(long userId, CreateUserAddressRequest request) {
        User user = userRepository.findById(userId)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // If the new address is set as default, we need to set existing default to false
        if (Boolean.TRUE.equals(request.isDefault())) {
            resetDefaultAddresses(userId);
        }

        UserAddress address = new UserAddress();
        address.setUser(user);
        address.setReceiverName(request.receiverName());
        address.setReceiverPhone(request.receiverPhone());
        address.setProvinceId(request.provinceId());
        address.setDistrictId(request.districtId());
        address.setWardId(request.wardId());
        address.setStreet(request.street());
        
        // If this is the user's first address, force it to be default
        List<UserAddress> existingAddresses = userAddressRepository.findByUserIdAndDeletedAtIsNull(userId);
        if (existingAddresses.isEmpty()) {
            address.setDefault(true);
        } else {
            address.setDefault(request.isDefault());
        }

        return UserAddressResponse.fromEntity(userAddressRepository.save(address));
    }

    @Override
    @Transactional
    public UserAddressResponse updateUserAddress(long id, long userId, UpdateUserAddressRequest request) {
        UserAddress address = userAddressRepository.findByIdAndUserIdAndDeletedAtIsNull(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserAddress", "id", id));

        if (Boolean.TRUE.equals(request.isDefault()) && !address.isDefault()) {
            resetDefaultAddresses(userId);
        }

        address.setReceiverName(request.receiverName());
        address.setReceiverPhone(request.receiverPhone());
        address.setProvinceId(request.provinceId());
        address.setDistrictId(request.districtId());
        address.setWardId(request.wardId());
        address.setStreet(request.street());
        
        // Only allow changing default if they are setting it to true, 
        // or if they are setting to false we should allow it but maybe another address should become default? 
        // For simplicity, we just set whatever is requested.
        address.setDefault(request.isDefault());

        return UserAddressResponse.fromEntity(userAddressRepository.save(address));
    }

    @Override
    @Transactional
    public void deleteUserAddress(long id, long userId) {
        UserAddress address = userAddressRepository.findByIdAndUserIdAndDeletedAtIsNull(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserAddress", "id", id));

        address.setDeletedAt(Instant.now());
        userAddressRepository.save(address);
    }

    private void resetDefaultAddresses(long userId) {
        List<UserAddress> currentAddresses = userAddressRepository.findByUserIdAndDeletedAtIsNull(userId);
        for (UserAddress addr : currentAddresses) {
            if (addr.isDefault()) {
                addr.setDefault(false);
                userAddressRepository.save(addr);
            }
        }
    }
}
