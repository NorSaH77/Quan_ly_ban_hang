package com.hoag.quanlybanhang.dto;

import java.util.List;

/**
 * DTO cho yêu cầu tạo đơn hàng mới
 * - khachHangId: ID của khách hàng đặt hàng
 * - chiTiet: danh sách chi tiết sản phẩm trong đơn hàng
 */
public class TaoDonHangRequest {

    private Long khachHangId;
    private List<ChiTietDto> chiTiet;

    // === Getters và Setters ===

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public List<ChiTietDto> getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(List<ChiTietDto> chiTiet) {
        this.chiTiet = chiTiet;
    }

    /**
     * Lớp con chứa thông tin chi tiết từng sản phẩm trong đơn hàng
     * - sanPhamId: ID sản phẩm
     * - soLuong: số lượng mua
     */
    public static class ChiTietDto {

        private Long sanPhamId;
        private Integer soLuong;

        // === Getters và Setters ===

        public Long getSanPhamId() {
            return sanPhamId;
        }

        public void setSanPhamId(Long sanPhamId) {
            this.sanPhamId = sanPhamId;
        }

        public Integer getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(Integer soLuong) {
            this.soLuong = soLuong;
        }
    }
}
