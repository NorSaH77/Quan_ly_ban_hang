package com.hoag.quanlybanhang.controller;

import com.hoag.quanlybanhang.dto.ApiResponse;
import com.hoag.quanlybanhang.entity.KhachHang;
import com.hoag.quanlybanhang.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller quản lý Khách hàng (Customer)
 * Cung cấp các API: lấy danh sách, tạo mới, cập nhật
 */
@RestController
@RequestMapping("/api/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    /**
     * Lấy danh sách tất cả khách hàng
     * GET /api/khach-hang
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<KhachHang>>> getAll() {
        List<KhachHang> list = khachHangService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách khách hàng thành công", list));
    }

    /**
     * Tạo khách hàng mới
     * POST /api/khach-hang
     * Body: { "hoTen": "...", "soDienThoai": "...", "email": "...", "diaChi": "..." }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<KhachHang>> create(@RequestBody KhachHang khachHang) {
        KhachHang created = khachHangService.create(khachHang);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tạo khách hàng thành công", created));
    }

    /**
     * Cập nhật thông tin khách hàng theo ID
     * PUT /api/khach-hang/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KhachHang>> update(
            @PathVariable Long id,
            @RequestBody KhachHang khachHang) {
        KhachHang updated = khachHangService.update(id, khachHang);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật khách hàng thành công", updated));
    }
}
