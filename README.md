# 📱 Ứng dụng Android - E-Learning Course

Đây là ứng dụng **Android frontend** cho hệ thống học trực tuyến **E-Learning Course**, kết nối với backend Spring Boot và cung cấp giao diện người dùng để đăng ký, học tập và thanh toán khóa học.

---

## 🚀 Chức năng chính (cho người dùng)

- 👤 **Quản lý tài khoản**
  - Đăng ký, đăng nhập
  - Cập nhật thông tin cá nhân
  - Quên mật khẩu

- 🎓 **Học tập và tương tác**
  - Xem danh sách khóa học
  - Xem thông tin chi tiết khóa học
  - Bookmark / yêu thích khóa học
  - Xem video bài giảng
  - Trả lời câu hỏi trắc nghiệm sau bài học
  - Xem và tải chứng chỉ hoàn thành

- 💬 **Tương tác xã hội**
  - Đánh giá và bình luận khóa học

- 🔍 **Tìm kiếm & lọc**
  - Tìm kiếm khóa học theo tên
  - Tình kiếm theo tên tutor

- 💳 **Thanh toán**
  - Tích hợp thanh toán VNPay
  - Giao dịch bảo mật và xác nhận đơn hàng

---

## 🗂️ Cấu trúc thư mục chính

```
com.example.e_learningcourse/
├── adapter/        # Adapter cho RecyclerView
├── api/            # Gọi API backend
├── base/           # BaseActivity, BaseFragment
├── constants/      # Hằng số dùng chung
├── data/           # Lưu trữ dữ liệu tạm thời
├── model/          # Các lớp dữ liệu (User, Course,...)
├── repository/     # Xử lý giao tiếp giữa ViewModel và API
├── ui/             # Giao diện người dùng (màn hình chính)
├── utils/          # Hàm tiện ích chung
├── App.java        # Lớp khởi tạo ứng dụng
└── MainActivity.java
```

---

## ▶️ Hướng dẫn chạy ứng dụng

1. Clone repo:
```bash
git clone https://github.com/lenhutanh/e-learning_course_app_front_end.git
```

2. Mở bằng **Android Studio** (đã cài đặt Android SDK)

3. Đồng bộ Gradle nếu cần, sau đó nhấn **Run** ▶ để cài lên thiết bị hoặc máy ảo

4. Đảm bảo backend đã chạy và cấu hình đúng `BASE_URL` trong file `api/ApiClient.java`:

```java
public static final String BASE_URL = "https://<backend-host>/api/";
```

---

## 🧪 Yêu cầu hệ thống

- Android Studio Flamingo trở lên
- Android SDK 33+
- Internet (để gọi API)

---

## 👨‍🎓 Sinh viên thực hiện

- **Lê Nhựt Anh** - 22110279  
- **Nguyễn Sang Huy** - 22110333  

---

> 📦 Repo backend: [github.com/abcdefghuy/ELearning-Course](https://github.com/abcdefghuy/ELearning-Course)
>  
> 📦 Repo frontend: [github.com/lenhutanh/e-learning_course_app_front_end](https://github.com/lenhutanh/e-learning_course_app_front_end)
