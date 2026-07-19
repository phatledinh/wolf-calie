# Category Module Context

## Overview
This module manages categories in the catalog domain, supporting hierarchical organization (parent-child categories), image association, sorting order, and active/inactive status.

## Design Decisions
- **Slug Generation:** Uses a shared `SlugUtil` to generate normalized, ASCII-friendly slugs from category names. Uniqueness is enforced at the service level by appending `-N` when collisions occur.
- **Hierarchical Structure:** `Category` has a self-referencing relation `parentCategory` (ManyToOne).
- **Soft Delete:** Implemented via Hibernate `@SQLRestriction("deleted_at IS NULL")`. Deleting a category simply sets `deletedAt`, excluding it from normal queries.
- **DTOs:** `CategoryResponse` only exposes `parentId` rather than the entire parent object to keep API payloads lean and flat.

## Known Limitations
- When deleting a category, we first check if it has active child categories. If it does, we throw an `InvalidRequestException` rather than cascading the soft-delete to children.
- No full tree traversal endpoint is provided by default. If a deeply nested tree view is required in the frontend, a dedicated query/endpoint should be added.
