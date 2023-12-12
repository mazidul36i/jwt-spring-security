package com.gliesestudio.os.core.service.user;

import com.gliesestudio.os.core.dto.ResponseAcknowledgementDto;
import com.gliesestudio.os.core.dto.user.UserDto;
import com.gliesestudio.os.core.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface to define all the abstracted methods required in user details
 *
 * @author MazidulIslam
 * @since 12-12-2023
 */
public interface UserService {

    UserDto getUserByUserId(UUID userId) throws ResourceNotFoundException;

    UserDto getUserByUsername(String username) throws ResourceNotFoundException;

    UserDto getUserByEmail(String email) throws ResourceNotFoundException;

    // Only for admins
    List<UserDto> getUsersByQuery(String query, Pageable pageable);

    ResponseAcknowledgementDto updateUserDetails(/* UserUpdateRequestDto */); // Also update (lastModified)

    ResponseAcknowledgementDto updateUsername(String username);

}
