# UserAddress Module Context

## Domain Details
- **Entity**: `UserAddress`
- **Table**: `user_addresses`
- **Purpose**: Manages delivery addresses for users. Each user can have multiple addresses but only one default address.

## Design Decisions
- **Package Structure**: Created a dedicated `feature/useraddress` package rather than placing it in `feature/user` to avoid package bloat, even though it is tightly coupled with `User`.
- **Soft Delete**: Inherits `deletedAt` from `BaseEntity`. Deletions are soft. Fetch queries filter out deleted records.
- **Default Address Logic**: 
  - If a user has no addresses and creates one, it is forced to be the default address.
  - If a user sets an address as default (either on creation or update), any existing default address for that user is updated to `isDefault = false`.
- **Security & Authorization**: The controller extracts the `userId` from the JWT token via `@AuthenticationPrincipal`. Users can only query, create, update, or delete their own addresses. The `userId` is never exposed in the URL or request bodies to prevent manipulation.

## Technical Details
- Extends `BaseEntity` for `id`, `createdAt`, `updatedAt`, `deletedAt`.
- `@ManyToOne` association with `User` entity.
- Endpoints mapped to `/api/v1/users/me/addresses` per `API_SPEC.md`.

## Refactor Log
- Initial implementation of full CRUD. 11 tests implemented and passing.
