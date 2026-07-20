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
✅ Phase 2: User, Unit Test, Integration Test

## In Progress
- Phase 0: Validation, MapStruct, Base Entity
- Phase 2: Role, UserAddress

## Next Tasks
- [P2] Implement Role CRUD
- [P3] Implement Auth Module (Login/Register/Refresh Token)

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
- [ ] Role
- [x] User
- [ ] UserAddress
- [x] Unit Test
- [x] Integration Test


### Phase 3 — Authentication
- [ ] Login
- [ ] Register
- [ ] Refresh Token (cơ bản)
- [ ] Logout
- [ ] GET /users/me
- [ ] Unit Test
- [ ] Integration Test


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
