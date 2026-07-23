# Security Module Context

## Domain Details
- **Scope**: Toàn bộ hệ thống Backend.
- **Mục đích**: Chịu trách nhiệm thiết lập các cơ chế xác thực (Authentication), phân quyền (Authorization) và tiện ích bảo mật (Security Utilities) cho ứng dụng.

## Design Decisions
- **Tách biệt Logic Xác Thực (AuthenticationFacade)**:
  - Khởi tạo `AuthenticationFacade` và `AuthenticationFacadeImpl` đóng vai trò là cầu nối (Facade) để lấy thông tin user hiện tại (ID, Email) từ `SecurityContextHolder`.
  - Quyết định này giúp loại bỏ hoàn toàn việc parse `Jwt` thủ công ở tầng Controller (như `UserController` hay `UserAddressController`).
  - Hỗ trợ xử lý lỗi tập trung: Ném `InvalidTokenException` khi token thiếu claim hoặc bị lỗi format, giúp `GlobalExceptionHandler` bắt và trả về mã lỗi 401 Unauthorized thay vì 500 Internal Server Error.
- **Bảo vệ Type-Casting**: 
  - Tại `AuthenticationFacadeImpl`, cấu hình an toàn khi lấy JWT Claims: xử lý cả định dạng `Number` và `String` để phòng tránh `ClassCastException` nếu Jackson (hoặc thư viện parse JSON) đổi kiểu dữ liệu lúc runtime (Long/Integer).
  
## Technical Details
- **Component**: `@Component AuthenticationFacadeImpl` implements `AuthenticationFacade`.
- **Luồng hoạt động**:
  1. `getJwt()`: Truy cập `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`.
  2. Validate sự tồn tại của Token và kiểu của Token (`instanceof Jwt`).
  3. Lấy claim `userId` (hoặc `subject` cho email) và validate format (chuyển đổi an toàn về kiểu `Long`).
  4. Quăng exception `InvalidTokenException` nếu có bất kỳ bước nào sai lệch.
- **Tích hợp**: Được sử dụng trực tiếp qua Dependency Injection (`@RequiredArgsConstructor` hoặc constructor-based injection) ở bất cứ Controller hoặc Service nào cần biết "Ai đang thực hiện Request này".
