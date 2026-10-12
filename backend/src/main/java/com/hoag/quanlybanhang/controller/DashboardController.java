package com.hoag.quanlybanhang.controller;

import com.hoag.quanlybanhang.dto.ApiResponse;
import com.hoag.quanlybanhang.dto.DashboardStatsDto;
import com.hoag.quanlybanhang.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller cho Dashboard (Trang tổng quan)
 * Cung cấp API thống kê: doanh thu, đơn hàng, khách hàng, sản phẩm sắp hết
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Lấy thống kê tổng quan cho Dashboard
     * GET /api/dashboard/stats
     * Trả về: doanhThuNgay, tongDonHang, tongKhachHang, sanPhamSapHet
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getStats() {
        DashboardStatsDto stats = dashboardService.getStats();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy thống kê thành công", stats));
    }
}
