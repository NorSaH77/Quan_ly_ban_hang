package com.hoag.quanlybanhang.config;

import com.hoag.quanlybanhang.entity.NguoiDung;
import com.hoag.quanlybanhang.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataInitializer - Tạo dữ liệu mặc định khi ứng dụng khởi động
 *
 * Implements CommandLineRunner → phương thức run() sẽ được gọi tự động khi Spring Boot khởi động.
 * Nếu bảng NguoiDung trống (chưa có tài khoản nào), sẽ tự động tạo:
 * - 1 tài khoản Admin
 * - 1 tài khoản Nhân viên (Staff)
 *
 * Mật khẩu mặc định: 123456 (đã được mã hóa bằng BCrypt)
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Chỉ tạo dữ liệu mặc định nếu bảng NguoiDung chưa có bản ghi nào
        if (nguoiDungRepository.count() == 0) {

            // === Tạo tài khoản Admin ===
            NguoiDung admin = new NguoiDung();
            admin.setHoTen("Admin");
            admin.setEmail("admin@example.com");
            admin.setMatKhau(passwordEncoder.encode("123456")); // Mã hóa mật khẩu
            admin.setVaiTro(NguoiDung.VaiTro.ADMIN);
            nguoiDungRepository.save(admin);

            // === Tạo tài khoản Nhân viên ===
            NguoiDung staff = new NguoiDung();
            staff.setHoTen("Nhan Vien");
            staff.setEmail("staff@example.com");
            staff.setMatKhau(passwordEncoder.encode("123456")); // Mã hóa mật khẩu
            staff.setVaiTro(NguoiDung.VaiTro.STAFF);
            nguoiDungRepository.save(staff);

            // In thông báo ra console để developer biết
            System.out.println("Da tao tai khoan mac dinh: admin@example.com / 123456");
        }
    }
}
