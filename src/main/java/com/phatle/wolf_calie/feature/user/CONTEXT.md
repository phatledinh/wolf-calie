# User Module Context

## 1. Domain Overview
The User module handles all core identity management and profile operations. This is a foundational module that other modules (like Cart, Order, Review) will reference via foreign keys.

## 2. Technical Stack
- **Entity**: `User` mapped to the `users` table.
- **Enums**: `UserStatus` (ACTIVE, INACTIVE, BANNED), `Gender` (MALE, FEMALE, OTHER).
- **Service**: `UserServiceImpl` implements `UserService`.
- **Controller**: Exposes REST endpoints at `/api/v1/users`.

## 3. Key Design Decisions
- **Soft Delete**: `deleteUser` uses soft-deletion by updating the `deleted_at` field to maintain referential integrity in a high-volume e-commerce system.
- **Security Check**: The current setup uses Spring Security. `getCurrentUser` is implemented using the `Jwt` token's subject (email) rather than retrieving user details from a session state, aligning with the stateless JWT architecture.
- **DTO Transformation**: Entity to DTO conversion is strictly localized within `UserResponse.fromEntity()`.

## 4. Pending/Future Enhancements
- **RBAC**: Implementation of roles (`user_role` mapping) is deferred to the Auth/RBAC module.
- **Caching**: Future consideration to cache frequently accessed public profiles.
