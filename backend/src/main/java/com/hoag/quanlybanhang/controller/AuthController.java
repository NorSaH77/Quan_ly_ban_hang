package com.hoag.quanlybanhang.controller;

import com.hoag.quanlybanhang.dto.ApiResponse;
import com.hoag.quanlybanhang.dto.ChangePasswordRequest;
import com.hoag.quanlybanhang.dto.LoginRequest;
import com.hoag.quanlybanhang.dto.LoginResponse;
import com.hoag.quanlybanhang.entity.NguoiDung;
import com.hoag.quanlybanhang.repository.NguoiDungRepository;
import com.hoag.quanlybanhang.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller xử lý xác thực người dùng (Authentication)
 * - Đăng nhập: kiểm tra email + mật khẩu, trả về JWT token
 * - Xem thông tin cá nhân: lấy từ SecurityContext
 * - Đổi mật khẩu: xác minh mật khẩu cũ, mã hóa mật khẩu mới
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * API Đăng nhập
     * POST /api/auth/login
     * Body: { "email": "...", "password": "..." }
     * Trả về: JWT token + thông tin người dùng
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        // Bước 1: Tìm người dùng theo email
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(request.getEmail())
                .orElse(null);

        // Bước 2: Kiểm tra email tồn tại và mật khẩu đúng
        if (nguoiDung == null || !passwordEncoder.matches(request.getPassword(), nguoiDung.getMatKhau())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Email hoặc mật khẩu không đúng", null));
        }

        // Bước 3: Tạo JWT token
        String token = jwtUtil.generateToken(nguoiDung);

        // Bước 4: Tạo response chứa token + thông tin người dùng
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setEmail(nguoiDung.getEmail());
        loginResponse.setHoTen(nguoiDung.getHoTen());
        loginResponse.setVaiTro(nguoiDung.getVaiTro().name()); // Chuyển enum → String

        return ResponseEntity.ok(new ApiResponse<>(true, "Đăng nhập thành công", loginResponse));
    }

    /**
     * API Xem thông tin cá nhân
     * GET /api/auth/me
     * Header: Authorization: Bearer <token>
     * Trả về: thông tin người dùng hiện tại
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCurrentUser() {
        // Lấy email từ SecurityContext (đã được JwtFilter xác thực)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Tìm người dùng theo email
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // Trả về thông tin cần thiết (không trả mật khẩu)
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", nguoiDung.getId());
        userInfo.put("hoTen", nguoiDung.getHoTen());
        userInfo.put("email", nguoiDung.getEmail());
        userInfo.put("vaiTro", nguoiDung.getVaiTro().name());

        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy thông tin thành công", userInfo));
    }

    /**
     * API Đổi mật khẩu
     * POST /api/auth/change-password
     * Body: { "oldPassword": "...", "newPassword": "..." }
     * Yêu cầu: phải đăng nhập (có JWT token hợp lệ)
     */
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestBody ChangePasswordRequest request) {

        // Bước 1: Lấy người dùng hiện tại từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // Bước 2: Kiểm tra mật khẩu cũ có đúng không
        if (!passwordEncoder.matches(request.getOldPassword(), nguoiDung.getMatKhau())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Mật khẩu cũ không đúng", null));
        }

        // Bước 3: Mã hóa mật khẩu mới và lưu
        nguoiDung.setMatKhau(passwordEncoder.encode(request.getNewPassword()));
        nguoiDungRepository.save(nguoiDung);

        return ResponseEntity.ok(new ApiResponse<>(true, "Đổi mật khẩu thành công", null));
    }
}
