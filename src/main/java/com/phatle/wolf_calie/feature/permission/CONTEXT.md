# Context: Permission Module

## Overview
This module handles the CRUD operations for Permissions. A Permission defines a granular access right within the system, typically mapping to a specific API endpoint and HTTP method (e.g., `GET /api/v1/users`). 

## Design Decisions
1. **Unidirectional Many-to-Many**: Although the database schema supports a Many-to-Many relationship between `Role` and `Permission`, we implemented this relationship as unidirectional (from `Role` to `Permission`). The `Permission` entity does NOT contain a `Set<Role>` field. This keeps the `Permission` entity simple and avoids potential circular reference issues during serialization. The `Role` entity (to be implemented) will be the owning side and maintain the `@JoinTable`.
2. **No Audit Fields**: Following the specific schema for permissions in `DATABASE.md`, the `Permission` entity does not include generic `created_at` or `updated_at` timestamps.

## Known Limitations
- The system currently prevents creating duplicate permissions based on `name` or the combination of `apiPath` + `method`.

## Refactor Log
- [2026-07-19] Initial implementation of Permission CRUD (Entity, Repository, DTOs, Service, Controller).
