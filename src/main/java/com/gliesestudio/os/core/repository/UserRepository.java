package com.gliesestudio.os.core.repository;

import com.gliesestudio.os.core.dto.user.UserDto;
import com.gliesestudio.os.core.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * {@link JpaRepository} for {@link UserModel} to connect and communicate with database
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    /**
     * Find {@link UserDto} by query
     *
     * @param query user's firstName, lastName, fullName, email, or username
     * @return list of {@link UserDto} based on the search result
     */
    @Query("SELECT u FROM UserModel u WHERE LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(u.username) LIKE LOWER(CONCAT('%', :q, '%')) ORDER BY u.firstName, u.lastName")
    List<UserDto> findByQuery(@Param("q") String query);

    /**
     * Find Optional {@link UserModel} by username ignoring case
     *
     * @param username case-insensitive username
     * @return Optional {@link UserModel} based on search result
     */
    Optional<UserModel> findByUsernameIgnoreCase(String username);

    /**
     * Find Optional {@link UserModel} by email ignoring case
     *
     * @param email case-insensitive email
     * @return Optional {@link UserModel} based on search result
     */
    Optional<UserModel> findByEmailIgnoreCase(String email);

    /**
     * Find Optional {@link UserModel} by email or username ignoring case
     *
     * @param email    case-insensitive email
     * @param username case-insensitive username
     * @return Optional {@link UserModel} based on search result
     */
    Optional<UserModel> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);
}