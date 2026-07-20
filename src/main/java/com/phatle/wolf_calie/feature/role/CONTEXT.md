# Role Module

## Description
This module handles CRUD operations for Roles within the Identity & Access phase. Roles are essential for Role-Based Access Control (RBAC), and they directly aggregate multiple Permissions. 
A Role can be assigned to Users to dictate their access level within the system.

## Design Decisions
- **No Soft Delete**: By project rules for the Role entity, there is NO soft deletion. Instead, the entity employs an `isActive` boolean flag to toggle a role's status.
- **Unidirectional Mapping**: The relationship between `Role` and `Permission` is `@ManyToMany` via the `permission_role` table, and is defined unidirectionally from `Role` to `Permission`. This avoids circular references and keeps the entity structure clean.
- **Audit Fields**: Uses standard `createdAt` and `updatedAt` mapped via Hibernate's `@CreationTimestamp` and `@UpdateTimestamp` to maintain a consistent audit trail.

## Entities
- `Role`: Represents a role in the database. Contains an `isActive` flag, standard audit fields, and a set of `Permissions`.

## Endpoints
- `GET /api/v1/roles` - List all roles with pagination
- `GET /api/v1/roles/{id}` - Get a specific role by ID
- `POST /api/v1/roles` - Create a new role with assigned permissions
- `PUT /api/v1/roles/{id}` - Update a role's details and/or permission set
- `DELETE /api/v1/roles/{id}` - Disable a role (sets `isActive` to false)
