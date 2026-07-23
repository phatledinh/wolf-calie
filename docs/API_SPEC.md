# API Specification (Enterprise Edition)

> System API Documentation for Fashion E-Commerce.
> Designed for OpenAPI 3 / Swagger integration.

---

## 1. Global Standards & Conventions

### Base URL & Versioning
```
Development: http://localhost:8080/api/v1
Production:  https://api.example.com/api/v1
```
*Rule: No breaking changes allowed in `v1`. Any major contract changes must spawn `v2`.*

### HTTP Status Codes
| Code | Meaning | Usage |
|---|---|---|
| `200` | OK | Successful GET, PUT |
| `201` | Created | Successful POST |
| `204` | No Content | Successful DELETE |
| `400` | Bad Request | Validation errors, Malformed JSON |
| `401` | Unauthorized | Missing or invalid JWT |
| `403` | Forbidden | JWT valid but lack Roles/Permissions |
| `404` | Not Found | Resource does not exist |
| `409` | Conflict | Duplicate data, Race conditions |
| `500` | Server Error | Internal exceptions |

### Response Wrapper (`ApiResponse<T>`)
All endpoints MUST return data wrapped in this uniform format.

**Success Response:**
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
```

**Pagination Response:**
```json
{
  "success": true,
  "data": {
    "content": [ { ... } ],
    "page": 1,
    "size": 20,
    "totalElements": 200,
    "totalPages": 10
  }
}
```

**Failure Response (Explicit Error Code):**
```json
{
  "success": false,
  "code": "PRODUCT_OUT_OF_STOCK",
  "message": "Product is out of stock",
  "data": null
}
```
*Common Error Codes: `USER_NOT_FOUND`, `EMAIL_ALREADY_EXISTS`, `VOUCHER_EXPIRED`, `VOUCHER_NOT_FOUND`, `PAYMENT_FAILED`, `INSUFFICIENT_STOCK`.*

### RESTful Rules
- **Master Data** (Product, Brand, Category, Role...): Full CRUD (`GET`, `POST`, `PUT`, `DELETE`).
- **Transaction Data** (Order, Payment, Delivery...): NO `DELETE`. Use verbs like `PUT /orders/{id}/cancel` instead.

### Idempotency
Critical mutation APIs (e.g., Checkout) MUST accept an `Idempotency-Key` header.
```http
Idempotency-Key: 7fca7b82-...
```
If a user double-clicks checkout, the second request with the same key returns the exact same successful response without double-charging.

---

## 2. Authentication & Identity

### Auth (JWT & Refresh Token via HttpOnly Cookie)
- `POST /auth/login` 🔓 (Returns Access Token in JSON, Refresh Token in HttpOnly Cookie)
- `POST /auth/register` 🔓
- `POST /auth/refresh` 🔓 (Reads Refresh Token from HttpOnly Cookie)
- `POST /auth/logout` 🔒 (Clears HttpOnly Cookie)

### Current User
- `GET /users/me` 🔒
- `GET /users/me/addresses` 🔒
- `POST /users/me/addresses` 🔒
- `PUT /users/me/addresses/{id}` 🔒
- `DELETE /users/me/addresses/{id}` 🔒
- `GET /users/me/wishlist` 🔒
- `POST /users/me/wishlist` 🔒 (Body: `{"productId": 1}`)
- `DELETE /users/me/wishlist/{productId}` 🔒
- `GET /users/me/orders` 🔒
- `GET /users/me/vouchers` 🔒
- `GET /users/me/notifications` 🔒
- `PUT /users/me/notifications/read-all` 🔒

### User Management (Admin)
- `GET /users` 🔒
- `GET /users/{id}` 🔒
- `POST /users` 🔒
- `PUT /users/{id}` 🔒
- `DELETE /users/{id}` 🔒

---

## 3. Catalog (Master Data)

### Products
- `GET /products` 🔓
  - *Query Params:* `category`, `brand`, `collection`, `tag`, `color`, `size`, `sort`, `page`, `size`
  - *Example:* `/products?category=shirt&brand=nike&color=black&size=L&sort=price_desc`
- `GET /products/{slug}` 🔓
- `POST /products` 🔒 (Admin)
- `PUT /products/{id}` 🔒 (Admin)
- `DELETE /products/{id}` 🔒 (Admin)

**Product Variants:**
- `POST /products/{id}/variants` 🔒 (Admin)
- `PUT /products/{id}/variants/{variantId}` 🔒 (Admin)
- `DELETE /products/{id}/variants/{variantId}` 🔒 (Admin)

**Product Images:**
- `POST /products/{id}/images` 🔒 (Admin)
- `PUT /products/{id}/images/{imageId}` 🔒 (Admin) (Set as primary)
- `DELETE /products/{id}/images/{imageId}` 🔒 (Admin)

### Categories, Brands, Collections, Tags, Colors, Sizes
*All support full CRUD for Admin, GET for Public.*
- `GET|POST /categories`, `PUT|DELETE /categories/{id}`
- `GET|POST /brands`, `PUT|DELETE /brands/{id}`
- `GET|POST /collections`, `PUT|DELETE /collections/{id}`
- `GET|POST /tags`, `PUT|DELETE /tags/{id}`
- `GET|POST /colors`, `PUT|DELETE /colors/{id}`
- `GET|POST /sizes`, `PUT|DELETE /sizes/{id}`

---

## 4. Sales (Transaction Data)

### Cart
- `GET /cart` 🔒
- `POST /cart/items` 🔒
- `PUT /cart/items/{itemId}` 🔒 (Update quantity / `selected` boolean)
- `DELETE /cart/items/{itemId}` 🔒

### Checkout & Orders
- `POST /orders/checkout` 🔒 (Requires `Idempotency-Key` header)
- `GET /orders` 🔒 (Admin sees all)
- `GET /orders/{id}` 🔒
- `PUT /orders/{id}/cancel` 🔒 (User/Admin can cancel)
- `POST /orders/{id}/return` 🔒 (User requests return)
- `PUT /orders/{id}/status` 🔒 (Admin updates flow)
- `PUT /orders/{id}/deliveries` 🔒 (Admin updates tracking/actual fee)

---

## 5. Marketing & Communication

### Vouchers
- `GET /vouchers/public` 🔓
- `POST /vouchers/validate` 🔒 (Body: `{"code": "SUMMER20"}`)
- `POST /vouchers/save` 🔒 (Body: `{"voucherId": 1}`)
- `POST|PUT|DELETE` `/vouchers` 🔒 (Admin)

### Reviews
- `GET /products/{id}/reviews` 🔓
- `POST /orders/items/{orderItemId}/reviews` 🔒 (Verified Purchase only)

### Banners & Articles
- `GET|POST /banners`, `PUT|DELETE /banners/{id}`
- `GET|POST /articles`, `PUT|DELETE /articles/{id}`

---

## 6. System & Infrastructure

### Uploads
- `POST /uploads/images` 🔒
  - *Response must return an object for file management:*
    ```json
    {
      "id": 128,
      "url": "https://cdn.example.com/uploads/products/abc123.jpg"
    }
    ```

### RBAC (Role-Based Access Control)
- `GET|POST /roles`, `PUT|DELETE /roles/{id}` 🔒 (Admin)
- `GET|POST /permissions`, `PUT|DELETE /permissions/{id}` 🔒 (Admin)

### Inventory (Audit)
- `GET /inventory/transactions` 🔒 (Admin logs)
- `POST /inventory/adjust` 🔒 (Admin manual adjustment)

---

## 7. API Documentation Generation
This API Spec follows standards fully compatible with **OpenAPI 3 / Swagger**. In the implementation phase, `@RestController` classes MUST be annotated with `@Tag` and `@Operation` to generate live Swagger documentation at `/swagger-ui.html`.