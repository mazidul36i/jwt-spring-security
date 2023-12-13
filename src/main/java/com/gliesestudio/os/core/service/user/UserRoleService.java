package com.gliesestudio.os.core.service.user;

import com.gliesestudio.os.core.dto.ResponseAcknowledgementDto;
import com.gliesestudio.os.core.dto.user.UserDto;
import com.gliesestudio.os.core.enums.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Service interface to define all the abstracted methods required in user role
 *
 * @author MazidulIslam
 * @implNote Implementation service class {@link UserRoleServiceImpl}
 * @since 10-12-2023
 */
public interface UserRoleService {

    /**
     * Find list of assigned user roles enums by userId
     *
     * @param userId {@link UUID} of the registered user
     * @return List of {@link UserRole}
     */
    List<UserRole> findUserRoleByUserId(UUID userId);

    /**
     * Fetch list of assigned role and convert them into collection of {@link GrantedAuthority}
     *
     * @param userId {@link UUID} of the registered user
     * @return collection of {@link GrantedAuthority}
     */
    Collection<? extends GrantedAuthority> getAuthorities(UUID userId);

 /*
    // TODO implement these services
    // only for admins
    List<UserDto> getUsersByRoleAssigned(UserRole role, Pageable pageable);

    // Only for admins // cannot assign or remove higher roles
    ResponseAcknowledgementDto addUserRoles(*//* dto *//*);

    // Only for admins // cannot assign or remove higher roles
    ResponseAcknowledgementDto removeUserRoles(*//* dto *//*);*/

}
