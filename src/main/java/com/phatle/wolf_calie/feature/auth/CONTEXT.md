# Auth Module Context

## 1. Tổng Quan (Overview)
Module này chịu trách nhiệm quản lý quy trình xác thực (Authentication) của toàn bộ hệ thống, bao gồm các chức năng: Đăng ký, Đăng nhập, Làm mới Token, Lấy thông tin user hiện tại và Đăng xuất.

## 2. Quyết Định Thiết Kế (Design Decisions)
- **Cơ chế Access Token & Refresh Token:**
  - `Access Token` được trả về qua Body JSON để Client tự quản lý. Hạn sử dụng ngắn (mặc định 15 phút).
  - `Refresh Token` được Server tự động thiết lập thông qua **HttpOnly Cookie** (`SameSite=Lax`, `Path=/api/v1/auth`). Hạn sử dụng dài (mặc định 7 ngày).
  - *Lý do:* Giải pháp này ngăn chặn hiệu quả các cuộc tấn công **XSS** (do JavaScript không thể đọc được HttpOnly Cookie) và phòng chống **CSRF** (nhờ cơ chế SameSite).
- **Multi-device Login (Đăng nhập đa thiết bị):**
  - Cho phép người dùng đăng nhập trên nhiều thiết bị cùng lúc. Mỗi lần Login thành công, DB sẽ tạo ra một hàng `RefreshToken` mới. Logout ở thiết bị nào thì chỉ vô hiệu hoá token ở thiết bị đó.
- **Quản lý cấu hình (Configuration):**
  - Đọc cấu hình JWT bằng Record `JwtProperties` (`@ConfigurationProperties(prefix = "jwt")`) để kiểm soát type-safe tốt hơn thay vì rải rác annotation `@Value`.
- **Dependency Injection:**
  - Tuân thủ 100% Constructor Injection cho các Service và Controller. Không dùng `@Autowired` ở mức Field.

## 3. Hạn Chế & Cân Nhắc (Known Limitations & Trade-offs)
- **Rác dữ liệu (Database Clutter):**
  - *Vấn đề:* Do cơ chế Multi-device Login, việc user login liên tục nhiều lần trên cùng 1 thiết bị sẽ sinh ra nhiều dòng token khác nhau. 
  - *Giải pháp hiện tại:* Trong `RefreshTokenRepository` đã có hàm `deleteAllExpiredBefore()` nhưng chưa được kích hoạt tự động. Sau này cần viết một Cron Job (Scheduled Task) để quét dọn mỗi ngày 1 lần.
- **Logic xử lý Cookie ở Controller:**
  - *Vấn đề:* `AuthController` đang chứa các hàm helper private để lấy và tạo Cookie (`extractRefreshTokenFromCookie`, `setRefreshTokenCookie`...). 
  - *Kế hoạch:* Nên tách ra thành một Class tĩnh (ví dụ `CookieUtils`) để tiện dùng lại cho các tính năng khác nếu cần.

## 4. Nhật Ký Thay Đổi (Refactor Log)
- **[2026-07-20] - Refactor cấu trúc và chuẩn hoá Security:**
  - Sửa toàn bộ đường dẫn package cũ (`vn.hoidanit.*`) sang đúng cấu trúc dự án (`com.phatle.wolf_calie.feature.auth.*`).
  - Thay đổi chiến lược lưu Refresh Token từ gửi thẳng Response Body sang dạng Cookie (HttpOnly).
  - Loại bỏ các khối ném lỗi `try/catch` lộn xộn ở Controller. Bắn thẳng các Exception kế thừa từ Runtime (`DuplicateResourceException`, `InvalidTokenException`, `ResourceNotFoundException`) ra ngoài để cho `GlobalExceptionHandler` bắt.
  - Fix lỗi Spring Boot không parse được `@Value` lúc khởi động bằng cách chuyển sang `JwtProperties`.
  - Cập nhật 100% Test Coverage (Unit & Integration) sử dụng `@MockitoBean` (thay thế cho `@MockBean` cũ).
