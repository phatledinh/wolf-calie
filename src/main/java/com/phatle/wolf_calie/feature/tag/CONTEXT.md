# Tag Module

## Description
This module handles CRUD operations for Tags in the catalog domain. Tags are used to group products into specific themes or collections (e.g. New Arrivals, Summer Sale).

## Entities
- `Tag`: Extends `BaseEntity`, represents a tag in the database with soft-delete capabilities.

## Endpoints
- `GET /api/v1/tags` - List all tags
- `GET /api/v1/tags/{id}` - Get a specific tag
- `POST /api/v1/tags` - Create a new tag
- `PUT /api/v1/tags/{id}` - Update a tag
- `DELETE /api/v1/tags/{id}` - Soft-delete a tag
