package com.phatle.wolf_calie.feature.user;

import com.phatle.wolf_calie.dto.ResultPaginationDTO;
import com.phatle.wolf_calie.feature.user.dto.CreateUserRequest;
import com.phatle.wolf_calie.feature.user.dto.UpdateUserRequest;
import com.phatle.wolf_calie.feature.user.dto.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    UserResponse getUserById(long id);

    UserResponse getCurrentUser(String email);

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(long id, UpdateUserRequest request);

    void deleteUser(long id);

    ResultPaginationDTO getAllUsers(Pageable pageable);
}
