# Tiến Độ Dự Án (Project Status)

> File này để theo dõi tiến độ tổng thể và cách chạy dự án.
> Cập nhật sau mỗi cuộc hội thoại quan trọng.

---

## 1. Thông Tin Chung
- **Dự án:** Wolf Calie - Hệ thống E-Commerce Thời Trang
- **Tech Stack:** Spring Boot 4, JWT, MySQL, JPA
- **Trạng thái:** Đang khởi tạo các module lõi

## 2. Tiến Độ Các Module

| Module | Phần Trăm | Trạng Thái | Ghi Chú |
|---|---|---|---|
| **Core & Exception** | 100% | Đã xong | Xử lý GlobalExceptionHandler, ApiResponse |
| **Security Config** | 90% | Đã xong | JWT (Oauth2 Resource Server), tắt CSRF |
| **User Module** | 100% | Đã xong | CRUD cơ bản, Soft Delete, Unit & Integration Tests |

## 3. To-Do (Việc tiếp theo)
- Implement Auth Module (Login/Register/Refresh Token)
- Implement Role-Based Access Control (RBAC)
- Khởi tạo Category & Product modules
