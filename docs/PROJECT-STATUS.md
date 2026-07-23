# Project Status
Last updated: 2026-07-20 | By: @ledinhphat (updated by AI)

> AI: update this file at the end of every session when asked. Follow this exact format. Keep it concise — under 150 lines.

## Completed
✅ PROJECT-RULES.md
✅ ARCHITECTURE.md
✅ DATABASE.md
✅ API_SPEC.md
✅ Authorization Workflow
✅ AI Development Workflow
✅ Backend folder structure
✅ Phase 0: Project Skeleton, GlobalExceptionHandler, ApiResponse, SecurityConfig, JwtConfig, OpenAPI / Swagger Config
✅ Phase 1: Permission CRUD, Category CRUD, Brand CRUD, Color CRUD, Size CRUD, Tag CRUD
✅ Phase 2: User, Role, UserAddress, Unit Test, Integration Test
✅ Phase 3: Auth Module (Login/Register/Refresh Token/Logout/Get Me)

## In Progress
- Phase 0: Validation, MapStruct

## Next Tasks
- [P4] Implement Catalog Module (Product, ProductVariant, ProductImage)

## Milestones

### Phase 0 — Foundation
- [x] Project Skeleton
- [x] Configuration
- [x] GlobalExceptionHandler
- [x] ApiResponse
- [x] SecurityConfig
- [x] JwtConfig
- [ ] Validation Config
- [ ] MapStruct Config
- [x] OpenAPI / Swagger Config
- [x] Base Entity (Audit Fields)
- [x] Unit Test
- [x] Integration Test


### Phase 1 — Master Data
- [x] Permission
- [x] Category
- [x] Brand
- [x] Color
- [x] Size
- [x] Tag
- [x] Collection
- [x] Unit Test
- [x] Integration Test


### Phase 2 — Identity & Access
- [x] Role
- [x] User
- [x] UserAddress
- [x] Unit Test
- [x] Integration Test


### Phase 3 — Authentication
- [x] Login
- [x] Register
- [x] Refresh Token (cơ bản)
- [x] Logout
- [x] GET /users/me
- [x] Unit Test
- [x] Integration Test


### Phase 4 — Catalog
- [ ] Product
- [ ] ProductVariant
- [ ] ProductImage
- [ ] CollectionProduct
- [ ] ProductTag
- [ ] Unit Test
- [ ] Integration Test


### Phase 5 — Inventory
- [ ] InventoryService
- [ ] InventoryTransaction
- [ ] Stock Adjustment
- [ ] Optimistic Lock
- [ ] Unit Test
- [ ] Integration Test


### Phase 6 — Sales (E-commerce Core)
- [ ] Cart
- [ ] CartItem
- [ ] CheckoutUseCase
- [ ] Order
- [ ] OrderItem
- [ ] Payment
- [ ] Delivery
- [ ] OrderCreatedEvent
- [ ] InventoryListener
- [ ] NotificationListener
- [ ] Unit Test
- [ ] Integration Test


### Phase 7 — Marketing & Customer
- [ ] Voucher
- [ ] Wishlist
- [ ] Review
- [ ] Notification
- [ ] Unit Test
- [ ] Integration Test


### Phase 8 — Infrastructure
- [ ] Refresh Token Rotation
- [ ] StorageService
- [ ] Upload API
- [ ] Scheduler
- [ ] Redis Cache
- [ ] Unit Test
- [ ] Integration Test


### Phase 9 — Security & Optimization
- [ ] RBAC Middleware
- [ ] Search
- [ ] Pagination
- [ ] Sorting
- [ ] Filter
- [ ] MySQL Fulltext
- [ ] Database Index Optimization
- [ ] Performance Optimization
- [ ] Unit Test
- [ ] Integration Test


## Session History

### 2026-07-20
- Standardized `DATABASE.md`, `API_SPEC.md`, and `PROJECT-RULES.md` for enterprise consistency.
- Unified the audit model for Master Data entities by adopting a common `BaseEntity` structure.
- Defined Soft Delete policies for applicable entities and standardized audit fields across the database schema.
- Refactored API specifications to expose `Color` and `Size` as top-level REST resources (`/colors`, `/sizes`) instead of nested `/attributes/*`.
- Added project rules requiring all implementations to strictly follow `DATABASE.md` and `API_SPEC.md`, treating them as the single source of truth.
- Implemented `Size` CRUD (Entity, Repository, Service, Controller, DTOs).
- Wrote comprehensive Unit and Integration tests for `Size` (all 13 passed).
- Updated `SecurityConfig` to allow public GET access to `/api/v1/sizes`.
- Added `CONTEXT.md` for `Size` module.
- Implemented `BaseEntity` as a unified audit model for all Master Data entities.
- Implemented `Tag` CRUD (Entity, Repository, Service, Controller, DTOs).
- Wrote comprehensive Unit and Integration tests for `Tag`.
- Added `CONTEXT.md` for `Tag` module.
- Implemented `Collection` CRUD (Entity, Repository, Service, Controller, DTOs).
- Wrote Unit and Integration tests for `Collection`.
- Allowed public GET access to `/api/v1/collections` in `SecurityConfig`.
- Updated `DATABASE.md` to include `is_active`, `created_at`, `updated_at` for Role.
- Implemented `Role` CRUD (Entity, Repository, Service, Controller, DTOs) with unidirectional `@ManyToMany` mapping to `Permission`.
- Wrote and passed comprehensive Unit Tests (`RoleServiceImplTest`) and Integration Tests (`RoleControllerIntegrationTest`) adhering to custom project conventions.
- Created `feature/useraddress` package for UserAddress CRUD to avoid bloating `feature/user`.
- Implemented `UserAddress` CRUD (Entity extending `BaseEntity`, Repository, Service, Controller, DTOs).
- Designed endpoints to map to `/api/v1/users/me/addresses` and retrieve `userId` securely from JWT context.
- Successfully passed 6 Unit Tests and 5 Integration Tests for UserAddress module.
- Added `CONTEXT.md` for `UserAddress` module.
- Implemented `AuthenticationFacade` in the `security` package to decouple JWT parsing from controllers.
- Updated `UserController` and `UserAddressController` to extract user state via `AuthenticationFacade`.
- Added `CONTEXT.md` for the `security` package.
- Implemented Auth Module (Phase 3) including `Login`, `Register`, `RefreshToken`, and `Logout`.
- Created `RefreshToken` entity and repository.
- Refactored `CustomUserDetailsService` and `JwtService` to inject `userId` and `scope` into JWT claims.
- Added `/api/v1/users/me` endpoint to `UserController` using `AuthenticationFacade`.
- Refactored Auth Module to use HttpOnly Cookie for Refresh Token, enhancing security against XSS.
- Refactored AuthServiceImpl and AuthController to correctly handle JWT configurations via `JwtProperties`.
- Replaced outdated `@MockBean` with `@MockitoBean` in Auth integration tests.
- Replaced `com.fasterxml.jackson` with `tools.jackson` to resolve test DI issues.
- Updated `API_SPEC.md` and added `CONTEXT.md` for Auth Module.
- Wrote and passed comprehensive Unit and Integration tests for the refactored Auth Module.
