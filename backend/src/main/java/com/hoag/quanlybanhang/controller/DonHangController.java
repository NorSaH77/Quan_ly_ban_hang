package com.hoag.quanlybanhang.controller;

import com.hoag.quanlybanhang.dto.ApiResponse;
import com.hoag.quanlybanhang.dto.CapNhatTrangThaiRequest;
import com.hoag.quanlybanhang.dto.TaoDonHangRequest;
import com.hoag.quanlybanhang.entity.DonHang;
import com.hoag.quanlybanhang.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller quản lý Đơn hàng (Order)
 * Cung cấp các API: lấy danh sách, tạo đơn, xem chi tiết, cập nhật trạng thái
 */
@RestController
@RequestMapping("/api/don-hang")
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    /**
     * Lấy danh sách tất cả đơn hàng
     * GET /api/don-hang
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<DonHang>>> getAll() {
        List<DonHang> list = donHangService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách đơn hàng thành công", list));
    }

    /**
     * Tạo đơn hàng mới
     * POST /api/don-hang
     * Body: { "khachHangId": 1, "chiTiet": [{ "sanPhamId": 1, "soLuong": 2 }, ...] }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DonHang>> create(@RequestBody TaoDonHangRequest request) {
        DonHang created = donHangService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tạo đơn hàng thành công", created));
    }

    /**
     * Xem chi tiết đơn hàng theo ID
     * GET /api/don-hang/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DonHang>> getById(@PathVariable Long id) {
        DonHang donHang = donHangService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy đơn hàng thành công", donHang));
    }

    /**
     * Cập nhật trạng thái đơn hàng
     * PUT /api/don-hang/{id}/trang-thai
     * Body: { "trangThai": "DA_XAC_NHAN" }
     */
    @PutMapping("/{id}/trang-thai")
    public ResponseEntity<ApiResponse<DonHang>> capNhatTrangThai(
            @PathVariable Long id,
            @RequestBody CapNhatTrangThaiRequest request) {
        DonHang updated = donHangService.capNhatTrangThai(id, request.getTrangThai());
        return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật trạng thái thành công", updated));
    }
}
