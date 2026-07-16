package com.phatle.wolf_calie.feature.user;

import com.phatle.wolf_calie.dto.ResultPaginationDTO;
import com.phatle.wolf_calie.exception.DuplicateResourceException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.user.dto.CreateUserRequest;
import com.phatle.wolf_calie.feature.user.dto.UpdateUserRequest;
import com.phatle.wolf_calie.feature.user.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(long id) {
        User user = userRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserResponse.fromEntity(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return UserResponse.fromEntity(user);
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("User", "email", request.email());
        }
        
        if (request.phone() != null && userRepository.existsByPhone(request.phone())) {
            throw new DuplicateResourceException("User", "phone", request.phone());
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        // Additional defaults are set in the User entity

        return UserResponse.fromEntity(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (request.phone() != null && !request.phone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(request.phone())) {
                throw new DuplicateResourceException("User", "phone", request.phone());
            }
            user.setPhone(request.phone());
        }

        user.setName(request.name());
        user.setGender(request.gender());
        user.setBirthday(request.birthday());
        user.setAvatar(request.avatar());

        return UserResponse.fromEntity(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Soft delete
        user.setDeletedAt(Instant.now());
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultPaginationDTO getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        // We might want to filter out soft deleted ones here, but standard JPA repository findAll will fetch them unless we override it.
        // Let's assume admin can see them or we handle it via custom query. For now, just return.

        List<UserResponse> listUser = userPage.getContent().stream()
                .filter(u -> u.getDeletedAt() == null)
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());

        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta(
                pageable.getPageNumber() + 1,
                pageable.getPageSize(),
                userPage.getTotalPages(),
                userPage.getTotalElements()
        );

        return new ResultPaginationDTO(meta, listUser);
    }
}
