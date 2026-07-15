# Database Schema (Enterprise Fashion E-Commerce)

> Highly normalized and optimized schema for a large-scale fashion e-commerce system.
> Update this file whenever schema changes.

---

## Entity Relationship Diagram

```text
[users] 1──N [user_addresses]
[users] 1──N [carts]
[users] 1──N [orders]
[users] 1──N [reviews]
[users] 1──N [user_vouchers] N──1 [vouchers]
[users] 1──1 [wishlists]
[users] 1──N [notifications]

[brands] 1──N [products]
[categories] 1──N [products]
[collections] N──N [products] (via collection_products)
[tags] N──N [products] (via product_tags)

[products] 1──N [product_variants]
[colors] 1──N [product_variants]
[sizes] 1──N [product_variants]

[product_variants] 1──N [product_images]
[product_variants] 1──N [inventory_transactions]

[carts] 1──N [cart_items] N──1 [product_variants]
[orders] 1──N [order_items] N──1 [product_variants]

[orders] 1──1 [payments]
[orders] 1──1 [deliveries]
[orders] 1──N [voucher_histories] N──1 [vouchers]

[order_items] 1──1 [reviews] (Verified purchase logic)

[banners] (Independent)
[articles] (Independent)
```

---

## 1. User & Account Domain

### users
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| name | VARCHAR(100) | NOT NULL | Full name |
| email | VARCHAR(255) | NOT NULL, UNIQUE | Login email |
| password | VARCHAR(255) | NOT NULL | BCrypt hash |
| phone | VARCHAR(20) | NULLABLE, UNIQUE | Phone number |
| gender | VARCHAR(20) | NULLABLE | MALE, FEMALE, OTHER |
| birthday | DATE | NULLABLE | |
| avatar | VARCHAR(255) | NULLABLE | |
| status | VARCHAR(50) | NOT NULL | ACTIVE, INACTIVE, BANNED |
| email_verified | BOOLEAN | NOT NULL, DEFAULT false | |
| phone_verified | BOOLEAN | NOT NULL, DEFAULT false | |
| last_login | TIMESTAMP | NULLABLE | |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP | |
| updated_at | TIMESTAMP | NULLABLE, ON UPDATE CURRENT_TIMESTAMP | |
| deleted_at | TIMESTAMP | NULLABLE | Soft delete timestamp |

### user_addresses
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| user_id | BIGINT | FK → users(id), NOT NULL | |
| receiver_name | VARCHAR(100) | NOT NULL | |
| receiver_phone| VARCHAR(20) | NOT NULL | |
| province_id | VARCHAR(50) | NOT NULL | API ID |
| district_id | VARCHAR(50) | NOT NULL | API ID |
| ward_id | VARCHAR(50) | NOT NULL | API ID |
| street | VARCHAR(255) | NOT NULL | Detailed address |
| is_default | BOOLEAN | NOT NULL, DEFAULT false | |

---

## 2. Catalog Domain (Products, Attributes, Organization)

### categories
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| parent_id | BIGINT | FK → categories(id), NULLABLE | |
| name | VARCHAR(255) | NOT NULL | |
| slug | VARCHAR(255) | NOT NULL, UNIQUE | |
| description | TEXT | NULLABLE | |
| image_url | VARCHAR(255) | NULLABLE | |
| sort_order | INT | NOT NULL, DEFAULT 0 | Display priority |
| is_active | BOOLEAN | NOT NULL, DEFAULT true | |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP | |
| updated_at | TIMESTAMP | NULLABLE | |
| deleted_at | TIMESTAMP | NULLABLE | |

### brands
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| name | VARCHAR(255) | NOT NULL | |
| slug | VARCHAR(255) | NOT NULL, UNIQUE | |
| website | VARCHAR(255) | NULLABLE | |
| country | VARCHAR(100) | NULLABLE | |
| description | TEXT | NULLABLE | |
| logo_url | VARCHAR(255) | NULLABLE | |
| is_active | BOOLEAN | NOT NULL, DEFAULT true | |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP | |
| deleted_at | TIMESTAMP | NULLABLE | |

### colors
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| name | VARCHAR(100) | NOT NULL | e.g. Black, Navy Blue |
| hex_code | VARCHAR(10) | NOT NULL | e.g. #000000 |
| sort_order | INT | NOT NULL, DEFAULT 0 | |

### sizes
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| name | VARCHAR(50) | NOT NULL | e.g. XS, S, M, L, XL |
| sort_order | INT | NOT NULL, DEFAULT 0 | |

### products
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| category_id | BIGINT | FK → categories(id), NOT NULL | |
| brand_id | BIGINT | FK → brands(id), NULLABLE | |
| name | VARCHAR(255) | NOT NULL | |
| slug | VARCHAR(255) | NOT NULL, UNIQUE | |
| description | TEXT | NULLABLE | |
| keywords | VARCHAR(500) | NULLABLE | SEO & Search keywords |
| search_count | INT | NOT NULL, DEFAULT 0 | Trending tracking |
| is_active | BOOLEAN | NOT NULL, DEFAULT true | |
| created_by | BIGINT | FK → users(id), NULLABLE | Admin who created |
| updated_by | BIGINT | FK → users(id), NULLABLE | Admin who last updated |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP | |
| updated_at | TIMESTAMP | NULLABLE, ON UPDATE CURRENT_TIMESTAMP | |
| deleted_at | TIMESTAMP | NULLABLE | |

### product_variants
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| product_id | BIGINT | FK → products(id), NOT NULL | |
| color_id | BIGINT | FK → colors(id), NULLABLE | |
| size_id | BIGINT | FK → sizes(id), NULLABLE | |
| price | DECIMAL(10,2) | NOT NULL | Base selling price |
| sale_price | DECIMAL(10,2) | NULLABLE | Discounted price |
| stock | INT | NOT NULL, DEFAULT 0 | Available inventory |
| sku | VARCHAR(100) | NOT NULL, UNIQUE | Barcode / SKU |
| version | INT | NOT NULL, DEFAULT 0 | Optimistic Locking |
| deleted_at | TIMESTAMP | NULLABLE | |

### product_images
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| product_id | BIGINT | FK → products(id), NOT NULL | |
| variant_id | BIGINT | FK → product_variants(id), NULLABLE | Maps image to specific variant |
| image_url | VARCHAR(255) | NOT NULL | |
| is_primary | BOOLEAN | NOT NULL, DEFAULT false | |
| sort_order | INT | NOT NULL, DEFAULT 0 | |

### collections & tags
**collections**: `id`, `name`, `slug`, `description`, `image_url`, `is_active`, `start_date`, `end_date`, `created_at`
**collection_products**: `collection_id`, `product_id` (Composite PK)
**tags**: `id`, `name`, `slug`
**product_tags**: `product_id`, `tag_id` (Composite PK)

---

## 3. Inventory Operations

### inventory_transactions
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| variant_id | BIGINT | FK → product_variants(id), NOT NULL| |
| type | VARCHAR(50) | NOT NULL | IMPORT, ORDER, RETURN, ADJUSTMENT |
| quantity | INT | NOT NULL | e.g. +100, -2 |
| reference_type| VARCHAR(50) | NULLABLE | e.g. 'ORDER', 'PO' (Purchase Order) |
| reference_id | BIGINT | NULLABLE | Link to order_id or po_id |
| created_by | BIGINT | FK → users(id), NULLABLE | Admin/User who caused the change|
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP | |

---

## 4. Cart, Order & Checkout Domain

### carts
**carts**: `id`, `user_id`, `created_at`, `updated_at`

### cart_items
**cart_items**: `id`, `cart_id`, `variant_id`, `quantity`, `selected` (BOOLEAN, DEFAULT true), `created_at`

### orders
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| user_id | BIGINT | FK → users(id), NOT NULL | |
| status | VARCHAR(50) | NOT NULL | PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED |
| receiver_name | VARCHAR(100) | NOT NULL | Snapshot of delivery info |
| receiver_phone| VARCHAR(20) | NOT NULL | Snapshot of delivery info |
| province_id | VARCHAR(50) | NOT NULL | Snapshot of delivery info |
| district_id | VARCHAR(50) | NOT NULL | Snapshot of delivery info |
| ward_id | VARCHAR(50) | NOT NULL | Snapshot of delivery info |
| street_address| VARCHAR(255) | NOT NULL | Snapshot of delivery info |
| note | TEXT | NULLABLE | Customer note |
| subtotal | DECIMAL(10,2) | NOT NULL | Sum of items |
| discount_amount| DECIMAL(10,2) | NOT NULL, DEFAULT 0 | |
| shipping_fee | DECIMAL(10,2) | NOT NULL | Amount charged to customer |
| total_amount | DECIMAL(10,2) | NOT NULL | subtotal + shipping - discount |
| estimated_delivery| DATE | NULLABLE | |
| cancel_reason | VARCHAR(255) | NULLABLE | |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP | |
| updated_at | TIMESTAMP | NULLABLE, ON UPDATE CURRENT_TIMESTAMP | |

### order_items
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| order_id | BIGINT | FK → orders(id), NOT NULL | |
| variant_id | BIGINT | FK → product_variants(id), NULLABLE | Soft relation, can be null if product deleted |
| product_name | VARCHAR(255) | NOT NULL | Snapshot data |
| variant_name | VARCHAR(255) | NOT NULL | Snapshot data (e.g. "Black - XL") |
| image | VARCHAR(255) | NULLABLE | Snapshot data |
| sku | VARCHAR(100) | NOT NULL | Snapshot data |
| quantity | INT | NOT NULL | |
| price | DECIMAL(10,2) | NOT NULL | Price at purchase time |

### payments
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| order_id | BIGINT | FK → orders(id), NOT NULL, UNIQUE | |
| method | VARCHAR(50) | NOT NULL | COD, VNPAY, MOMO, CREDIT_CARD |
| amount | DECIMAL(10,2) | NOT NULL | Should match order total |
| status | VARCHAR(50) | NOT NULL | PENDING, COMPLETED, FAILED, REFUNDED |
| transaction_id| VARCHAR(255) | NULLABLE | Gateway ID |
| paid_at | TIMESTAMP | NULLABLE | |
| gateway_response| JSON | NULLABLE | Raw webhook/response |
| refund_amount | DECIMAL(10,2) | NULLABLE | |
| refund_reason | VARCHAR(255) | NULLABLE | |

### deliveries
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| order_id | BIGINT | FK → orders(id), NOT NULL, UNIQUE | |
| provider | VARCHAR(100) | NULLABLE | GHTK, GHN, ViettelPost |
| shipping_method| VARCHAR(100) | NULLABLE | Standard, Express |
| tracking_code | VARCHAR(100) | NULLABLE | |
| actual_shipping_fee| DECIMAL(10,2)| NULLABLE | Cost charged by provider to merchant |
| status | VARCHAR(50) | NOT NULL | PREPARING, PICKED_UP, IN_TRANSIT, DELIVERED, RETURNED |
| shipped_at | TIMESTAMP | NULLABLE | |
| delivered_at | TIMESTAMP | NULLABLE | |
| returned_at | TIMESTAMP | NULLABLE | |

---

## 5. Marketing, Social & Interactions

### reviews
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| user_id | BIGINT | FK → users(id), NOT NULL | |
| product_id | BIGINT | FK → products(id), NOT NULL | For fast querying by product |
| order_item_id | BIGINT | FK → order_items(id), NOT NULL | Ensures Verified Purchase |
| rating | INT | NOT NULL | 1 to 5 stars |
| comment | TEXT | NULLABLE | |
| images | JSON | NULLABLE | List of image URLs |
| reply | TEXT | NULLABLE | Admin response |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP | |
| updated_at | TIMESTAMP | NULLABLE, ON UPDATE CURRENT_TIMESTAMP | |
| deleted_at | TIMESTAMP | NULLABLE | |

### vouchers
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | |
| code | VARCHAR(50) | NOT NULL, UNIQUE | e.g. SUMMER20 |
| description | TEXT | NULLABLE | |
| apply_scope | VARCHAR(50) | NOT NULL | ALL, CATEGORY, BRAND, PRODUCT |
| discount_type | VARCHAR(20) | NOT NULL | PERCENT, FIXED |
| discount_value| DECIMAL(10,2) | NOT NULL | |
| min_order_value| DECIMAL(10,2) | NOT NULL, DEFAULT 0 | |
| max_discount | DECIMAL(10,2) | NULLABLE | |
| start_date | TIMESTAMP | NOT NULL | |
| end_date | TIMESTAMP | NOT NULL | |
| usage_limit | INT | NULLABLE | Max global usage |
| max_usage_per_user| INT | NOT NULL, DEFAULT 1 | |
| used_count | INT | NOT NULL, DEFAULT 0 | |
| is_active | BOOLEAN | NOT NULL, DEFAULT true | |
| deleted_at | TIMESTAMP | NULLABLE | |

### voucher_targets
**voucher_targets**: `voucher_id`, `reference_type` (CATEGORY, BRAND, PRODUCT), `reference_id` (ID of the target entity) - Maps scope.

### user_vouchers
**user_vouchers**: `user_id`, `voucher_id`, `is_used` (Wallet system to collect vouchers). Composite PK.

### voucher_histories
**voucher_histories**: `id`, `user_id`, `voucher_id`, `order_id`, `discount_amount`, `used_at` (Audit log of usages).

### wishlists & wishlist_items
**wishlists**: `id`, `user_id`, `created_at`
**wishlist_items**: `id`, `wishlist_id`, `product_id`, `created_at`

### notifications
**notifications**: `id`, `user_id`, `title`, `content`, `type` (ORDER, VOUCHER, SYSTEM), `is_read` (BOOLEAN, DEFAULT false), `created_at`

### banners & articles
**banners**: `id`, `title`, `image_url`, `target_url`, `position`, `is_active`, `deleted_at`
**articles**: `id`, `title`, `slug`, `thumbnail`, `content`, `created_at`, `deleted_at`

---

## 6. System Tables & Indexing Strategies

### Roles & Permissions (RBAC)
**roles**: `id`, `name`, `description`
**permissions**: `id`, `name`, `api_path`, `method`, `module`
**user_role**: `user_id`, `role_id`
**permission_role**: `permission_id`, `role_id`
**refresh_tokens**: `id`, `token`, `user_id`, `expires_at`, `revoked`, `device_info`, `ip_address`

### Core Database Indexes

**Unique Indexes (B-Tree):**
- `users(email)`
- `products(slug)`
- `categories(slug)`
- `brands(slug)`
- `vouchers(code)`
- `product_variants(sku)`

**Performance Indexes (B-Tree):**
- `products(category_id)`
- `products(brand_id)`
- `product_variants(product_id)`
- `reviews(product_id)`
- `reviews(order_item_id)`
- `orders(user_id)`
- `orders(status)`
- `order_items(order_id)`
- `cart_items(cart_id)`
- `user_addresses(user_id)`
- `inventory_transactions(variant_id)`
- `voucher_histories(user_id)`
- `products(name)` *(Consider Full-Text Index or Elasticsearch if search becomes complex)*