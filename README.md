# TRÙM AI — Android

Ứng dụng troll: nhập câu hỏi hoặc dùng micro, chờ 2,4 giây, luôn hiển thị và đọc câu “Tui không biết, xin mời câu hỏi tiếp theo!”.

## Build APK
1. Mở thư mục `TrumAI` bằng Android Studio (JDK 17, Android SDK 35).
2. Đợi Gradle Sync hoàn thành.
3. Chọn **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. APK debug nằm tại `app/build/outputs/apk/debug/app-debug.apk`.

## Lưu ý
- Nhận dạng giọng nói phụ thuộc dịch vụ nhận dạng có trên thiết bị; có thể cần mạng.
- Giọng đọc dùng Android TextToSpeech tiếng Việt, chỉnh cao độ/tốc độ để vui nhộn; **không bảo đảm là giọng nữ miền Nam**. Muốn cố định giọng nữ miền Nam cần tích hợp tệp thu âm được cấp phép vào `res/raw` rồi dùng MediaPlayer thay TTS.
- Không cần API AI hay máy chủ.
