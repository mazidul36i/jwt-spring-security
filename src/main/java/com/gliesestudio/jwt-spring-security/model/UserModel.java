package com.gliesestudio.os.core.model;

import com.gliesestudio.os.core.service.user.UserRoleService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * User entity model for all the user accounts across the platform.
 *
 * @author MazidulIslam
 * @implNote <b color="red">Any method of this and it's extended classes must & always be final</b>
 * @since 09-12-2023
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EnableJpaAuditing
@Table(name = "`USER`")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "USERNAME", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String username;

    @Column(name = "FIRST_NAME", columnDefinition = "VARCHAR(30)")
    private String firstName;

    @Column(name = "LAST_NAME", columnDefinition = "VARCHAR(30)")
    private String lastName;

    @Column(name = "EMAIL", columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CREATED_ON", columnDefinition = "BIGINT")
    private Long createdOn;

    @Column(name = "LAST_MODIFIED", columnDefinition = "BIGINT")
    private Long lastModified;

    @Column(name = "IS_ACCOUNT_NON_EXPIRED", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isAccountNonExpired;

    @Column(name = "IS_ACCOUNT_NON_LOCKED", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isAccountNonLocked;

    /**
     * <p>Encrypt password before saving it</p>
     *
     * @param password provided by user
     */
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    /**
     * getAuthorities has implemented in UserRoleService
     *
     * @return this method here just returns an empty list
     * @see UserRoleService
     */
    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    /**
     * Checks if the user account is not expired.
     * We can use this attribute to create temporary user accounts which expires after a defined period of time.
     *
     * @return Boolean (isAccountExpired)
     */
    @Override
    public final boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    /**
     * Checks if the user account is not locked.
     * This allows admin to control user account to avoid miscellaneous user actions
     *
     * @return Boolean (isAccountNonLocked)
     */
    @Override
    public final boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    /**
     * Setting this true as default and can be updated at later development
     *
     * @return Boolean (true) defaulted
     */
    @Override
    public final boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    /**
     * Setting this true as default and can be updated at later development
     *
     * @return Boolean (true)
     */
    @Override
    public final boolean isEnabled() {
        return Boolean.TRUE;
    }

}
