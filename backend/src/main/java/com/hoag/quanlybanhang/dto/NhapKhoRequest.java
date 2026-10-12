package com.hoag.quanlybanhang.dto;

/**
 * DTO cho yêu cầu nhập kho sản phẩm
 * - sanPhamId: ID của sản phẩm cần nhập thêm
 * - soLuong: số lượng cần nhập thêm vào kho
 */
public class NhapKhoRequest {

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
