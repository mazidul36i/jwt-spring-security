package com.gliesestudio.jwt.enums;

/**
 * This is the common Role enum that is used accorss platform to assign to specific user
 * to provide platform access
 * @author MazidulIslam
 * @since 09-12-2023
 */
public enum UserRole {
    USER, // a general user with user access
    EDITOR, // an editor role is for a user who can write articles
    ADMIN, // a platform admin with limited access to specific region
    SYSTEM_ADMIN, // a system admin with limited access across the platform
    SUPER_ADMIN, // an admin with full access of the entire platform
    OWNER // platform owner
}
