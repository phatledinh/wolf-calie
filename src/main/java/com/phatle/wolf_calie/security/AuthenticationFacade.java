package com.phatle.wolf_calie.security;

public interface AuthenticationFacade {
    Long getCurrentUserId();
    String getCurrentUserEmail();
}
