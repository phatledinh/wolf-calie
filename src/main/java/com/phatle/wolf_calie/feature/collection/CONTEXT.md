# Collection Module Context

## Domain
Collections in the fashion e-commerce system allow grouping multiple products together for marketing, seasonal campaigns, or themed sets (e.g., "Summer Collection 2026"). 
- It maintains its own start and end dates for active campaigns.
- Soft Delete is enabled to preserve history.

## Technical Details
- **Entity**: `Collection` extends `BaseEntity`.
- **Database Table**: `collections`
- **Soft Delete**: Uses `@SQLRestriction("deleted_at IS NULL")`.
- **Endpoints**: `/api/v1/collections`
- **Permissions**: Public for GET requests, Admin/Moderator for mutations (POST/PUT/DELETE).

## Relationships
- Maps N-to-N with `Product` via `collection_products` (implementation in Catalog module).
