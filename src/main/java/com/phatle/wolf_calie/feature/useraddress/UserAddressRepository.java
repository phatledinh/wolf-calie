package com.phatle.wolf_calie.feature.useraddress;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUserIdAndDeletedAtIsNull(long userId);

    Optional<UserAddress> findByIdAndUserIdAndDeletedAtIsNull(long id, long userId);
    
    // We will handle setting isDefault to false manually in the service using the retrieved list
    // to avoid native queries if possible, but a custom query is fine too. Let's keep it simple.
}
