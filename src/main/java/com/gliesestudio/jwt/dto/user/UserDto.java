package com.gliesestudio.jwt.dto.user;

import com.gliesestudio.jwt.model.UserModel;
import com.gliesestudio.jwt.repository.UserRepository;
import com.gliesestudio.jwt.util.StringLiterals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * User dto class to be used by the application instead of using {@link UserModel}
 *
 * @author MazidulIslam
 * @since 11-12-2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private UUID userId;

    private String username;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private Long createdOn;

    /**
     * Contractor to create the user dto from {@link UserModel}
     * <br> This constructor will also be used by Spring {@link UserRepository} to create the dto
     *
     * @param userModel {@link UserModel}
     */
    public UserDto(UserModel userModel) {
        this.userId = userModel.getUserId();
        this.username = userModel.getUsername();
        this.firstName = userModel.getFirstName();
        this.lastName = userModel.getLastName();
        this.fullName = userModel.getFirstName() + StringLiterals.EMPTY_SPACE + userModel.getLastName();
        this.email = userModel.getEmail();
        this.createdOn = userModel.getCreatedOn();
    }
}
