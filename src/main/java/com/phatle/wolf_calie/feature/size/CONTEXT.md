# Size Module Context

## Overview
This module manages product sizes in the catalog domain, allowing the system to categorize products by their physical dimensions or standardized sizes (e.g. S, M, L, XL).

## Design Decisions
- **Basic Attributes:** The `Size` entity only stores `name` and `sort_order` as specified in the `sizes` table of `DATABASE.md`.
- **Soft Delete:** Implemented via Hibernate `@SQLRestriction("deleted_at IS NULL")`. Deleting a size sets the `deletedAt` timestamp, excluding it from normal queries while preserving historical data.
- **REST API:** Fully supports standard CRUD mapped under `/api/v1/sizes`. `GET` methods are open to the public (whitelisted in `SecurityConfig.java`), while mutations require `ADMIN` role.
- **Validation:** Uses Jakarta Bean Validation to ensure size names are not blank and fit database constraints (max 50 characters).

## Known Limitations
- Deleting a size currently only soft-deletes the master data record. If future features implement hard constraints (e.g. `ProductVariant` referencing this size), a constraint check may be required before allowing deletion to prevent orphan references.
