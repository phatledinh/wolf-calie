# Brand Module Context

## Purpose
The Brand module manages fashion brands in the e-commerce system. It handles creating, updating, and viewing brand information. Products belong to a single brand.

## Key Components
- `Brand`: The JPA entity mapping to the `brands` table. Uses soft delete (`deleted_at`).
- `BrandRepository`: Data access, including finding by unique slug.
- `BrandService` & `BrandServiceImpl`: Business logic, slug generation (`SlugUtil`), and DTO conversion.
- `BrandController`: REST endpoints (`/api/v1/brands`) following the standard JSON response format (`ApiResponse`).
- `dto/`: Contains `BrandResponse`, `CreateBrandRequest`, and `UpdateBrandRequest`.

## Dependencies
- Uses `SlugUtil` for generating unique slugs from brand names.
- Relies on global exception handling for resource not found and validation errors.
