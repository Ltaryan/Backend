
package com.HdfcLife.SecureAuthService.AuthService.service;


import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryTokenStore {

    private final Set<String> validTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * Stores a token in the set of valid, active tokens.
     * CORRECTED: Renamed from addToken to storeToken.
     */
    public void storeToken(String token) {
        validTokens.add(token);
    }

    /**
     * Checks if a token is present in the set of valid tokens.
     * CORRECTED: Renamed from isValidToken to isTokenValid.
     */
    public boolean isTokenValid(String token) {
        return validTokens.contains(token);
    }

    /**
     * Removes a token from the set, effectively invalidating it.
     * CORRECTED: Renamed from removeToken to invalidateToken.
     */
    public void invalidateToken(String token) {
        validTokens.remove(token);
    }

    public int getTokenCount() {
        return validTokens.size();
    }
}
