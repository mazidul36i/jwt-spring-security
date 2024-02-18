package com.gliesestudio.os.core.model;

import com.gliesestudio.os.core.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

/**
 * This entity is used to define roles for a user. This is used with jwt token to grant access to restricted APIs
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EnableJpaAuditing
@Table(name = "USER_ROLE")
public class UserRoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "ROLE", columnDefinition = "VARCHAR(100)")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "CREATED_ON", columnDefinition = "BIGINT")
    private Long createdOn;

    @Column(name = "LAST_MODIFIED", columnDefinition = "BIGINT")
    private Long lastModified;

}
