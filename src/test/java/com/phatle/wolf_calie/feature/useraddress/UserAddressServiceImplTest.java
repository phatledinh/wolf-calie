package com.phatle.wolf_calie.feature.useraddress;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.user.User;
import com.phatle.wolf_calie.feature.user.UserRepository;
import com.phatle.wolf_calie.feature.useraddress.dto.CreateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UpdateUserAddressRequest;
import com.phatle.wolf_calie.feature.useraddress.dto.UserAddressResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAddressServiceImplTest {

    @Mock
    private UserAddressRepository userAddressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAddressServiceImpl userAddressService;

    @Test
    @DisplayName("Should return list of addresses for given user ID")
    void getUserAddresses_validUserId_returnsList() {
        long userId = 1L;
        UserAddress address = new UserAddress();
        address.setId(10L);
        address.setReceiverName("John Doe");

        when(userAddressRepository.findByUserIdAndDeletedAtIsNull(userId)).thenReturn(List.of(address));

        List<UserAddressResponse> result = userAddressService.getUserAddresses(userId);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).id());
        assertEquals("John Doe", result.get(0).receiverName());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user not found during creation")
    void createUserAddress_userNotFound_throwsException() {
        long userId = 99L;
        CreateUserAddressRequest request = new CreateUserAddressRequest(
                "John", "0123456789", "P1", "D1", "W1", "123 St", true);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAddressService.createUserAddress(userId, request));
    }

    @Test
    @DisplayName("Should create address and set as default if it is the first address")
    void createUserAddress_firstAddress_forcesDefaultTrue() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);

        CreateUserAddressRequest request = new CreateUserAddressRequest(
                "John", "0123456789", "P1", "D1", "W1", "123 St", false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userAddressRepository.findByUserIdAndDeletedAtIsNull(userId)).thenReturn(List.of());
        when(userAddressRepository.save(any(UserAddress.class))).thenAnswer(i -> {
            UserAddress saved = i.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        UserAddressResponse result = userAddressService.createUserAddress(userId, request);

        assertTrue(result.isDefault());
        assertEquals("John", result.receiverName());
        verify(userAddressRepository).save(any(UserAddress.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent address")
    void updateUserAddress_notFound_throwsException() {
        long id = 10L;
        long userId = 1L;
        UpdateUserAddressRequest request = new UpdateUserAddressRequest(
                "John", "0123456789", "P1", "D1", "W1", "123 St", true);

        when(userAddressRepository.findByIdAndUserIdAndDeletedAtIsNull(id, userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAddressService.updateUserAddress(id, userId, request));
    }

    @Test
    @DisplayName("Should successfully update address")
    void updateUserAddress_found_updatesSuccessfully() {
        long id = 10L;
        long userId = 1L;
        UpdateUserAddressRequest request = new UpdateUserAddressRequest(
                "Jane", "0987654321", "P2", "D2", "W2", "456 Ave", true);

        UserAddress existing = new UserAddress();
        existing.setId(id);
        existing.setReceiverName("Old Name");
        existing.setDefault(false);

        UserAddress existingDefault = new UserAddress();
        existingDefault.setId(20L);
        existingDefault.setDefault(true);

        when(userAddressRepository.findByIdAndUserIdAndDeletedAtIsNull(id, userId)).thenReturn(Optional.of(existing));
        when(userAddressRepository.findByUserIdAndDeletedAtIsNull(userId)).thenReturn(List.of(existingDefault, existing));
        when(userAddressRepository.save(any(UserAddress.class))).thenAnswer(i -> i.getArgument(0));

        UserAddressResponse result = userAddressService.updateUserAddress(id, userId, request);

        assertEquals("Jane", result.receiverName());
        assertTrue(result.isDefault());
        assertFalse(existingDefault.isDefault()); // It should have been reset
        verify(userAddressRepository).save(existingDefault);
        verify(userAddressRepository).save(existing);
    }

    @Test
    @DisplayName("Should soft delete address successfully")
    void deleteUserAddress_found_softDeletes() {
        long id = 10L;
        long userId = 1L;
        UserAddress existing = new UserAddress();
        existing.setId(id);

        when(userAddressRepository.findByIdAndUserIdAndDeletedAtIsNull(id, userId)).thenReturn(Optional.of(existing));

        userAddressService.deleteUserAddress(id, userId);

        assertNotNull(existing.getDeletedAt());
        verify(userAddressRepository).save(existing);
    }
}
