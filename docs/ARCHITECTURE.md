# Architecture (Enterprise Design)

> System design overview. Update only during architecture review sessions.

---

## High-Level Architecture

```text
                         ┌─────────────────┐
                         │   Client (SPA)   │
                         └────────┬─────────┘
                                  │ HTTPS
                                  ▼
                         ┌─────────────────┐
                         │   Spring Boot    │
                         │   Application    │
                         └────────┬─────────┘
                                  │
              ┌───────────────────┼───────────────────┐
              ▼                   ▼                    ▼
     ┌────────────────┐  ┌────────────────┐  ┌────────────────┐
     │   Controller   │  │ Security Layer │  │ Exception      │
     │   (REST API)   │  │ (JWT + RBAC)   │  │ Handler        │
     └───────┬────────┘  └────────────────┘  └────────────────┘
             │
             ▼
     ┌─────────────────────────┐
     │   Application Layer     │ (Orchestration / Use Cases)
     └───────────┬─────────────┘
                 │
                 ▼
     ┌─────────────────────────┐
     │   Domain Services       │ (Business Logic)
     └───────────┬─────────────┘
                 │
                 ▼
     ┌─────────────────────────┐
     │   Repository Layer      │ (Spring Data JPA)
     └───────────┬─────────────┘
                 │
                 ▼
          ┌─────────────┐
          │    MySQL    │
          └─────────────┘
```

---

## Software Design Patterns

### 1. Application Layer vs Domain Services (Clean Architecture)
- **Domain Services**: (`ProductService`, `InventoryService`, `VoucherService`). Chỉ xử lý logic nghiệp vụ nội tại của thực thể đó.
- **Application Layer**: (`CheckoutService`, `PlaceOrderService`). Đóng vai trò điều phối (Orchestration) nhiều Domain Services.
  - Ví dụ: `CheckoutService` sẽ gọi `ProductService` (lấy giá), gọi `VoucherService` (áp dụng mã), gọi `OrderService` (tạo đơn).

### 2. Event-Driven Design (EDA)
- Không lạm dụng Event cho mọi thao tác CRUD để tránh khó debug.
- Chỉ sử dụng `ApplicationEventPublisher` cho các luồng nghiệp vụ cốt lõi để giảm Coupling:
  - `OrderCreatedEvent`
  - `OrderCancelledEvent`
  - `PaymentSuccessEvent`
  - `PaymentFailedEvent`
  - `InventoryLowEvent`
  - `UserRegisteredEvent`
- Thay vì `CheckoutService` gọi thẳng `sendEmail()` hay `deductStock()`, nó chỉ cần publish `OrderCreatedEvent`. Các module khác (`InventoryListener`, `NotificationListener`) sẽ tự động lắng nghe và xử lý.

### 3. Transaction Boundaries
- **Write Operations** (create, update, delete, checkout...): Bắt buộc dùng `@Transactional`.
- **Read Operations** (findById, search...): Bắt buộc dùng `@Transactional(readOnly = true)` để tối ưu hiệu năng DB và báo hiệu rõ ý đồ của code.

### 4. Validation Pipeline
Luồng xử lý Validation phải tuân thủ nghiêm ngặt theo thứ tự sau:
```text
HTTP Request
  ↓
DTO Validation (Dùng annotation @Valid, @NotNull ở Controller)
  ↓
Business Validation (Kiểm tra stock đủ? Voucher còn hạn? tại Service)
  ↓
Authorization (Kiểm tra quyền)
  ↓
Persistence (Lưu xuống DB)
  ↓
Response
```

### 5. Mapper Strategy
- Vì định hướng hệ thống Enterprise có số lượng DTO và Entity lớn, toàn bộ thao tác chuyển đổi dữ liệu bắt buộc dùng **MapStruct**. Không dùng Manual Mapping hay ModelMapper.

---

## Core Encapsulation Rules

### 1. Inventory Encapsulation
- `InventoryService` là **Nơi duy nhất** được quyền thay đổi số lượng kho (`increaseStock`, `decreaseStock`, `adjustStock`).
- Tuyệt đối cấm các Service khác (vd: `ProductService`, `OrderService`) gọi trực tiếp `variant.setStock(...)`. Điều này đảm bảo tính toàn vẹn của nghiệp vụ kho và dễ dàng audit lịch sử.

### 2. Storage Layer
- Mọi nghiệp vụ lưu trữ file/hình ảnh không phụ thuộc trực tiếp vào cách lưu (Local/Cloud).
- Định nghĩa interface `StorageService` với 3 hàm cơ bản: `upload()`, `delete()`, `generateUrl()`.
- Các implement hiện tại: `LocalStorageService`. Tương lai có thể dễ dàng chuyển sang `S3StorageService` mà không ảnh hưởng `ProductService`.

---

## Domain Modules & Dependency Graph

Để dễ quản lý, hệ thống được chia thành 5 Domain (Bối cảnh nghiệp vụ) độc lập.

```text
1. Identity
   ├── User
   ├── Role
   └── Permission

2. Catalog
   ├── Category
   ├── Brand
   ├── Product (Variants, Colors, Sizes, Images)
   └── Inventory

3. Sales
   ├── Cart
   ├── Order
   ├── Payment
   └── Delivery

4. Marketing
   ├── Voucher
   ├── Banner
   ├── Collection
   └── Tag

5. Communication
   └── Notification (Phục vụ mọi module)
```

### Dependency Flow (Chiều phụ thuộc)
Mũi tên `A → B` có nghĩa là "A phụ thuộc vào B".

```text
Sales (Order) → Catalog (Product, Inventory)
Sales (Order) → Marketing (Voucher)
Sales (Order) → Communication (Notification)
Sales (Payment) → Sales (Order)
Catalog (Product) → Catalog (Category, Brand)
Marketing (Collection, Tag) → Catalog (Product)
All Modules → Identity (User)
```

---

## Background Jobs (Scheduler)

Hệ thống Thời trang yêu cầu các tác vụ chạy nền định kỳ. Sử dụng **Spring Scheduler** (`@Scheduled`):
- Tự động hủy đơn hàng chưa thanh toán sau 30 phút.
- Tự động đánh dấu Voucher hết hạn.
- Đồng bộ trạng thái đơn hàng từ đối tác vận chuyển.
- Gửi thông báo/email chăm sóc khách hàng tự động.

---

## Search & Scalability Notes

### Search Roadmap
- **Current**: Sử dụng **MySQL Full-text Index** (`MATCH() AGAINST()`) cho các nghiệp vụ tìm kiếm cơ bản.
- **Future**: Chuyển sang **Elasticsearch** khi lượng sản phẩm > 100k hoặc cần tìm kiếm Fuzzy, NLP.

### Concurrency Control
- **Optimistic Locking**: Sử dụng `@Version` ở `product_variants` để ngăn chặn triệt để lỗi bán lố (Overselling) khi có Flash Sale. Kẻ đến sau sẽ nhận `OptimisticLockException`.

### Caching Strategy
- **Redis Cache (Spring Cache)**: Dùng để lưu trữ toàn bộ cây phân cấp của `Category` và danh sách cấu hình hệ thống nhằm giảm tải cho Database. Mọi thao tác ghi/xóa Category sẽ tự động invalidate cache.