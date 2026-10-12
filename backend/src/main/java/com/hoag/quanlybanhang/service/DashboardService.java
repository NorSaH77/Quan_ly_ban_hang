package com.hoag.quanlybanhang.service;

import com.hoag.quanlybanhang.dto.DashboardStatsDto;
import com.hoag.quanlybanhang.entity.DonHang;
import com.hoag.quanlybanhang.repository.DonHangRepository;
import com.hoag.quanlybanhang.repository.KhachHangRepository;
import com.hoag.quanlybanhang.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Service tính toán thống kê cho Dashboard (trang tổng quan)
 * - Doanh thu ngày hôm nay (loại trừ đơn đã hủy)
 * - Tổng số đơn hàng
 * - Tổng số khách hàng
 * - Sản phẩm sắp hết hàng (tồn kho <= 10)
 */
@Service
public class DashboardService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    /**
     * Lấy thống kê tổng quan cho Dashboard
     */
    public DashboardStatsDto getStats() {
        DashboardStatsDto stats = new DashboardStatsDto();

        // === 1. Tính doanh thu ngày hôm nay ===
        // Lấy khoảng thời gian từ 00:00:00 đến 23:59:59 của ngày hôm nay
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();           // 00:00:00
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);      // 23:59:59.999

        // Tìm đơn hàng trong ngày, loại trừ đơn đã hủy
        List<DonHang> donHangHomNay = donHangRepository
                .findByNgayTaoBetweenAndTrangThaiNot(startOfDay, endOfDay, "DA_HUY");

        // Tính tổng doanh thu bằng stream: cộng tất cả tongTien
        double doanhThu = donHangHomNay.stream()
                .mapToDouble(DonHang::getTongTien)
                .sum();
        stats.setDoanhThuNgay(doanhThu);

        // === 2. Tổng số đơn hàng (tất cả, không phân biệt trạng thái) ===
        stats.setTongDonHang(donHangRepository.count());

        // === 3. Tổng số khách hàng ===
        stats.setTongKhachHang(khachHangRepository.count());

        // === 4. Sản phẩm sắp hết hàng (tồn kho <= 10) ===
        stats.setSanPhamSapHet(sanPhamRepository.findBySoLuongTonLessThanEqual(10));

        return stats;
    }
}
