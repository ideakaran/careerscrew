package com.project.careerscrew.security;

import com.project.careerscrew.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExtractAuthUser {

    public static Authentication getAuthenticationObject() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static CustomUserDetails getCurrentUserPrinciple() {
        Authentication authentication = getAuthenticationObject();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal);
            }
        }
        return null;
    }

    public static Optional<Long> getCurrentUserId() {
        Optional<Long> optionalUserId = Optional.ofNullable(getCurrentUserPrinciple())
                .map(customUserDetails -> customUserDetails.getUserId());
        return optionalUserId;
    }

    public static List<UserRole> getCurrentUserRoles() {
        CustomUserDetails customUserDetails = getCurrentUserPrinciple();
        if (customUserDetails != null) {
            List<UserRole> userRoleList = customUserDetails.getAuthorities().stream()
                    .map(grantedAuthority -> UserRole.valueOf(grantedAuthority.toString()))
                    .collect(Collectors.toList());
            return userRoleList;
        }
        return Arrays.asList();
    }

    public static boolean isAdmin() {
        boolean isAdmin = getCurrentUserRoles().stream()
                .anyMatch(userRole -> userRole.equals(UserRole.ROLE_ADMIN) || userRole.equals(UserRole.ROLE_ADMIN) );
        return isAdmin;
    }

    public static boolean isUserEntityHasAdminRoles(UserEntity userEntity) {
        if(ObjectUtils.isEmpty(userEntity.getRoles())) {
            return false;
        }
        boolean isAdmin = userEntity.getRoles().stream()
                .anyMatch(userRole -> userRole.equals(UserRole.ROLE_ADMIN) || userRole.equals(UserRole.ROLE_ADMIN) );
        return isAdmin;
    }

    public static boolean hasAdminAccessOrMatchesUserId(Long userId) {
        if (userId == null) {
            return false;
        }
        if (isAdmin() || (getCurrentUserId().isPresent() && getCurrentUserId().get().equals(userId))) {
            return true;
        }
        return false;
    }

}
