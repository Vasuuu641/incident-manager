// NEW FILE: src/main/java/com/security/incidentmanager/util/SecurityUtils.java
package com.security.incidentmanager.util;

import org.springframework.security.core.Authentication;

public class SecurityUtils {
    // Single reusable method — replaces inline stream logic in every controller
    public static boolean isAdmin(Authentication authentication) {
        return authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}