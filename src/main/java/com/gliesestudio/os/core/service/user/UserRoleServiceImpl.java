package com.gliesestudio.os.core.service.user;

import com.gliesestudio.os.core.enums.UserRole;
import com.gliesestudio.os.core.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the service interface {@link UserRoleService}
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findUserRoleByUserId(UUID userId) {
        return userRoleRepository.findUserRolesByUserId(userId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(UUID userId) {
        List<UserRole> roles = userRoleRepository.findUserRolesByUserId(userId);
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.name())
        ).collect(Collectors.toList());
    }
}
