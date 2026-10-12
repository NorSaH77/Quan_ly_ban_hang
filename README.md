# Hệ Thống Quản Lý Bán Hàng

Ứng dụng web quản lý bán hàng đầy đủ tính năng: quản lý kho sản phẩm, khách hàng, đơn hàng, thống kê doanh thu - xây dựng bằng **Spring Boot 3** (Backend) + **Vanilla HTML/CSS/JS** (Frontend).

---

## 🚀 Tính Năng Chính

| Module | Chức năng |
|--------|-----------|
| **🔐 Xác thực** | Đăng nhập JWT, phân quyền Admin/Staff, đổi mật khẩu |
| **📊 Dashboard** | Thống kê doanh thu ngày, tổng đơn hàng, khách hàng, sản phẩm sắp hết |
| **📦 Kho & Sản phẩm** | CRUD sản phẩm, nhập kho, cảnh báo tồn kho thấp (≤10) |
| **🧾 Đơn hàng** | Tạo đơn, cập nhật trạng thái (Chờ xác nhận → Đang giao → Đã giao/Đã hủy), in đơn |
| **👥 Khách hàng** | CRUD khách hàng, lịch sử mua hàng, tìm kiếm |

---

## 🛠 Công Nghệ

| Layer | Tech Stack |
|-------|------------|
| **Backend** | Java 21, Spring Boot 3.2.5, Spring Data JPA, Spring Security, JWT (jjwt), Flyway, MySQL |
| **Frontend** | HTML5, CSS3 (Grid/Flexbox), Vanilla ES6+ (Fetch API, Modules) |
| **Build** | Maven 3.9+ |
| **Database** | MySQL 8.0+ |

---

## 📁 Cấu Trúc Dự Án

```
Quan_ly_ban_hang/
├── backend/                    # Spring Boot API
│   ├── src/main/java/.../      # Source code
│   │   ├── controller/         # REST Controllers
│   │   ├── service/            # Business Logic
│   │   ├── repository/         # JPA Repositories
│   │   ├── entity/             # JPA Entities
│   │   ├── dto/                # Data Transfer Objects
│   │   ├── config/             # Security, JWT, DataInitializer
│   │   └── QuanLyBanHangApplication.java
│   ├── src/main/resources/
│   │   ├── application.yml     # Cấu hình DB, JWT, Server
│   │   └── db/migration/       # Flyway SQL migrations
│   │       ├── V1__init.sql    # Schema + user mặc định
│   │       └── V2__sample_data.sql # Dữ liệu mẫu (15 SP, 8 KH, 10 ĐH)
│   └── pom.xml
├── frontend/                   # Static Web
│   ├── index.html              # Dashboard
│   ├── kho.html                # Quản lý sản phẩm
│   ├── don-hang.html           # Quản lý đơn hàng
│   ├── khach-hang.html         # Quản lý khách hàng
│   ├── dang-nhap.html          # Login
│   ├── doi-mat-khau.html       # Change password
│   ├── css/style.css           # Styling
│   └── js/api.js               # API client (JWT auth, toast, modal)
└── README.md
```

---

## ⚙️ Yêu Cầu Hệ Thống

- **Java 21+** (khuyên dùng JDK 21 LTS)
- **Maven 3.9+**
- **MySQL 8.0+** (hoặc MariaDB 10.6+)
- **Python 3** hoặc **Node.js** (để chạy frontend static server)

---

## 🏃 Hướng Dẫn Chạy

### 1. Cài đặt & Khởi động MySQL

```bash
# Ubuntu / WSL / Debian
sudo apt update && sudo apt install -y mysql-server
sudo service mysql start

# Tạo database & user (khớp với application.yml)
sudo mysql -e "
CREATE DATABASE quan_ly_ban_hang CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'qlbh'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON quan_ly_ban_hang.* TO 'qlbh'@'localhost';
FLUSH PRIVILEGES;
"
```

### 2. Chạy Migration Dữ Liệu (Schema + Sample Data)

```bash
# Chạy lần lượt V1 (schema) và V2 (dữ liệu mẫu)
sudo mysql quan_ly_ban_hang < backend/src/main/resources/db/migration/V1__init.sql
sudo mysql quan_ly_ban_hang < backend/src/main/resources/db/migration/V2__sample_data.sql
```

> **Lưu ý:** Flyway đang tắt (`flyway.enabled: false` trong `application.yml`), nên cần chạy SQL thủ công.

### 3. Build & Chạy Backend

```bash
cd backend

# Build (bỏ qua test, dùng Java 21 source/target)
mvn clean package -DskipTests -Dmaven.compiler.release=

# Chạy
java -jar target/quan-ly-ban-hang-0.0.1-SNAPSHOT.jar
```

Backend sẽ chạy tại: **http://localhost:8080**

Kiểm tra API:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"123456"}'
```

### 4. Chạy Frontend (Terminal riêng)

```bash
cd frontend

# Python 3 (có sẵn trên hầu hết Linux/macOS/WSL)
python3 -m http.server 5500

# Hoặc Node.js
npx serve -p 5500
```

Frontend tại: **http://localhost:5500/dang-nhap.html**

---

## 🔑 Tài Khoản Mặc Định

| Role | Email | Mật khẩu |
|------|-------|----------|
| **Admin** | admin@example.com | 123456 |
| **Staff** | staff@example.com | 123456 |

> Mật khẩu đã được hash BCrypt trong DB.

---

## 📡 API Endpoints Chính

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| POST | `/api/auth/login` | Đăng nhập, trả về JWT |
| GET | `/api/auth/me` | Thông tin user hiện tại |
| POST | `/api/auth/change-password` | Đổi mật khẩu |
| GET | `/api/dashboard/stats` | Thống kê dashboard |
| GET | `/api/san-pham` | Danh sách sản phẩm (phân trang, tìm kiếm) |
| POST | `/api/san-pham` | Tạo sản phẩm mới |
| PUT | `/api/san-pham/{id}` | Cập nhật sản phẩm |
| POST | `/api/san-pham/nhap-kho` | Nhập kho |
| GET | `/api/khach-hang` | Danh sách khách hàng |
| POST | `/api/khach-hang` | Tạo khách hàng |
| GET | `/api/don-hang` | Danh sách đơn hàng |
| POST | `/api/don-hang` | Tạo đơn hàng mới |
| PUT | `/api/don-hang/{id}/trang-thai` | Cập nhật trạng thái đơn |

> Tất cả API (trừ `/auth/login`) yêu cầu header: `Authorization: Bearer <JWT_TOKEN>`

---

## 🗂 Cấu Hình Quan Trọng (`backend/src/main/resources/application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/quan_ly_ban_hang?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: qlbh
    password: "123456"
  jpa:
    hibernate:
      ddl-auto: update      # Tự tạo/cập nhật bảng
    show-sql: true
  flyway:
    enabled: false          # Tắt Flyway auto-migration

jwt:
  secret: yourSuperSecretKey12345MustBeAtLeast32CharsLong!!
  expirationMs: 86400000    # 24h

server:
  port: 8080
```

---

## 🐛 Xử Lý Sự Cố Thường Gặp

| Lỗi | Giải Pháp |
|-----|-----------|
| `Access denied for user 'root'@'localhost'` | Chạy lệnh tạo user `qlbh` như bước 1, hoặc config root password |
| `Plugin 'mysql_native_password' is not loaded` | Dùng user mới (`qlbh`) thay vì root, MySQL 8.0+ dùng `caching_sha2_password` mặc định |
| `var` compile error | Đã sửa thành type tường minh (`DashboardStatsDto stats = new DashboardStatsDto()`) |
| Frontend "Failed to fetch" | Backend chưa chạy hoặc sai port (mặc định 8080) |
| CORS error | Thêm CORS config trong `SecurityConfig.java` cho port frontend (5500/3000) |

---

## 📦 Build Production

```bash
# Backend - build JAR production
cd backend
mvn clean package -DskipTests -Pprod

# Frontend - chỉ cần serve thư mục frontend/ qua Nginx/Apache
# Cấu hình proxy_pass http://localhost:8080 cho /api/
```

---

## 📄 License

Dự án mẫu cho mục đích học tập/đemo. Tự do sử dụng, chỉnh sửa.

---

## 👤 Tác Giả

**hoag** - Hệ thống Quản Lý Bán Hàng Spring Boot + Vanilla JS