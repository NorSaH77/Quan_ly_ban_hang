package com.hoag.quanlybanhang.dto;

import com.hoag.quanlybanhang.entity.SanPham;
import java.util.List;

/**
 * DTO chứa thống kê tổng quan cho Dashboard
 * - doanhThuNgay: doanh thu trong ngày hôm nay
 * - tongDonHang: tổng số đơn hàng trong hệ thống
 * - tongKhachHang: tổng số khách hàng
 * - sanPhamSapHet: danh sách sản phẩm sắp hết hàng (tồn kho <= 10)
 */
public class DashboardStatsDto {

    private double doanhThuNgay;
    private long tongDonHang;
    private long tongKhachHang;
    private List<SanPham> sanPhamSapHet;

    // === Getters và Setters ===

    public double getDoanhThuNgay() {
        return doanhThuNgay;
    }

    public void setDoanhThuNgay(double doanhThuNgay) {
        this.doanhThuNgay = doanhThuNgay;
    }

    public long getTongDonHang() {
        return tongDonHang;
    }

    public void setTongDonHang(long tongDonHang) {
        this.tongDonHang = tongDonHang;
    }

    public long getTongKhachHang() {
        return tongKhachHang;
    }

    public void setTongKhachHang(long tongKhachHang) {
        this.tongKhachHang = tongKhachHang;
    }

    public List<SanPham> getSanPhamSapHet() {
        return sanPhamSapHet;
    }

    public void setSanPhamSapHet(List<SanPham> sanPhamSapHet) {
        this.sanPhamSapHet = sanPhamSapHet;
    }
}
