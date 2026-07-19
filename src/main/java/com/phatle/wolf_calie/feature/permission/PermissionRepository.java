package com.phatle.wolf_calie.feature.permission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByName(String name);

    boolean existsByApiPathAndMethod(String apiPath, String method);
}
