package com.financetracker.utils;

import com.financetracker.entity.User;
import com.financetracker.services.UserServices.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class to retrieve the current authenticated user from Spring Security Context.
 */
public class UserUtils {

    /**
     * Retrieves the current authenticated User entity.
     * Assumes that the security principal is of type CustomUserDetails,
     * which holds the User entity.
     *
     * @return The currently authenticated User entity.
     * @throws IllegalStateException if no user is authenticated or the principal is invalid.
     */
    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUser();
        }

        // This exception should ideally not be thrown if security is configured correctly
        // (i.e., this endpoint requires authentication).
        throw new IllegalStateException("Current user principal is not available or not of the expected type.");
    }
}