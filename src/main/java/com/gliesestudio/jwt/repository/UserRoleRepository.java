package com.gliesestudio.jwt.repository;

import com.gliesestudio.jwt.enums.UserRole;
import com.gliesestudio.jwt.model.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * {@link JpaRepository} for {@link UserRoleModel} to connect and communicate with database
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, UUID> {

    /**
     * Get list of {@link UserRole} by userId
     *
     * @param userId for which user to find roles
     * @return list of {@link UserRole} enum
     */
    @Query("SELECT role FROM UserRoleModel WHERE userId = :userId")
    List<UserRole> findUserRolesByUserId(UUID userId);

}
