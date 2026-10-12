package com.hoag.quanlybanhang.controller;

import com.hoag.quanlybanhang.dto.ApiResponse;
import com.hoag.quanlybanhang.dto.NhapKhoRequest;
import com.hoag.quanlybanhang.entity.SanPham;
import com.hoag.quanlybanhang.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller quản lý Sản phẩm (Product)
 * Cung cấp các API: CRUD + nhập kho
 */
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    /**
     * Lấy danh sách tất cả sản phẩm
     * GET /api/san-pham
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<SanPham>>> getAll() {
        List<SanPham> list = sanPhamService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách sản phẩm thành công", list));
    }

    /**
     * Tạo sản phẩm mới
     * POST /api/san-pham
     * Body: { "maSanPham": "...", "tenSanPham": "...", ... }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SanPham>> create(@RequestBody SanPham sanPham) {
        SanPham created = sanPhamService.create(sanPham);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tạo sản phẩm thành công", created));
    }

    /**
     * Cập nhật sản phẩm theo ID
     * PUT /api/san-pham/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SanPham>> update(
            @PathVariable Long id,
            @RequestBody SanPham sanPham) {
        SanPham updated = sanPhamService.update(id, sanPham);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật sản phẩm thành công", updated));
    }

    /**
     * Xóa sản phẩm theo ID
     * DELETE /api/san-pham/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        sanPhamService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Xóa sản phẩm thành công", null));
    }

    /**
     * Nhập kho: cộng thêm số lượng vào tồn kho sản phẩm
     * POST /api/san-pham/nhap-kho
     * Body: { "sanPhamId": 1, "soLuong": 50 }
     */
    @PostMapping("/nhap-kho")
    public ResponseEntity<ApiResponse<SanPham>> nhapKho(@RequestBody NhapKhoRequest request) {
        SanPham updated = sanPhamService.nhapKho(request.getSanPhamId(), request.getSoLuong());
        return ResponseEntity.ok(new ApiResponse<>(true, "Nhập kho thành công", updated));
    }
}
