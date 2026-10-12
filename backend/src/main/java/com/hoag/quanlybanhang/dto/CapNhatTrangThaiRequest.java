package com.hoag.quanlybanhang.dto;

/**
 * DTO cho yêu cầu cập nhật trạng thái đơn hàng
 * Các trạng thái thường dùng: CHO_XAC_NHAN, DA_XAC_NHAN, DANG_GIAO, DA_GIAO, DA_HUY
 */
public class CapNhatTrangThaiRequest {

    private String trangThai;

    // === Getters và Setters ===

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
