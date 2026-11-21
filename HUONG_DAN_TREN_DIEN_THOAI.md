# Tạo APK Trùm AI trên điện thoại bằng GitHub

1. Trên Chrome, vào https://github.com và đăng nhập (hoặc tạo tài khoản).
2. Tạo repository mới tên `TrumAI`, chọn **Public** hoặc **Private**.
3. Giải nén file ZIP này. Tải **nội dung bên trong thư mục TrumAI** lên repository, bao gồm thư mục ẩn `.github/workflows` (quan trọng). Trên GitHub có thể dùng **Add file > Upload files**; nếu điện thoại không tải được cả thư mục, bật chế độ trang web cho máy tính hoặc dùng ứng dụng quản lý file hỗ trợ tải thư mục.
4. Vào tab **Actions** > **Tao APK Trum AI** > **Run workflow** > **Run workflow**.
5. Chờ build thành công (dấu tích xanh). Mở lượt chạy, kéo xuống **Artifacts**, chọn **TrumAI-APK** để tải ZIP.
6. Giải nén ZIP lấy `app-debug.apk`, mở file để cài. Nếu Android hỏi, cho phép cài ứng dụng từ nguồn này.

Lưu ý: Đây là APK debug để thử nghiệm. Giọng đọc sử dụng giọng tiếng Việt Text-to-Speech có sẵn trên thiết bị, không đảm bảo đúng giọng nữ miền Nam trên mọi máy. Chưa có APK build sẵn trong ZIP này.
