# Old Render

Mod Fabric client nhẹ cho **Minecraft Java 1.16.5**. Nó dùng ViaVersion làm lõi dịch giao thức để client cũ có thể thử kết nối tới server phiên bản mới hơn (mục tiêu mặc định: **1.21.11**). Ý tưởng tương thích nhiều phiên bản lấy cảm hứng từ [ViaFabric](https://github.com/ViaVersion/ViaFabric); đây là dự án độc lập, không phải bản build hay fork của ViaFabric.

## Build

Cần JDK 17 trở lên và kết nối Internet để Gradle tải Minecraft/Fabric/ViaVersion:

```sh
./gradlew build
```

JAR đã remap nằm ở `build/libs/old-render-mc-0.1.0.jar`. Cài Fabric Loader cho Minecraft 1.16.5, chép JAR này vào `mods`, rồi chạy profile Fabric.

## Chọn phiên bản server

Lần chạy đầu tạo file `config/old-render.properties`. Giá trị mặc định:

```properties
target-version=1.21.11
```

Đặt giá trị này thành phiên bản giao thức server cần kết nối, ví dụ `1.20.6`, rồi khởi động lại game. Đây là thiết lập chung cho mọi server; phiên bản phải được hỗ trợ bởi bản ViaVersion đóng gói cùng mod. Mod hiện không tự dò phiên bản server.

## Giới hạn

- Đây là dịch gói tin, không biến Minecraft 1.16.5 thành game client 1.21.11. Nội dung chỉ có ở phiên bản mới có thể thiếu hoặc hoạt động không đúng.
- Trước tiên nhắm tới server vanilla; server modded có block/item hoặc registry tùy chỉnh có thể không tương thích. Anti-cheat của server cũng có thể từ chối kết nối.
- Mod xử lý tương thích giao thức, không tăng FPS hay giảm cấu hình đồ họa. Nó không cần cài trên server.
- Bản mod này yêu cầu Java 17 trở lên do lõi ViaVersion; Minecraft 1.16.5 vẫn có thể chạy bằng Java 17 qua launcher.
- Dự án đóng gói lõi ViaVersion; xem giấy phép và cập nhật phiên bản tại <https://github.com/ViaVersion/ViaVersion>.
