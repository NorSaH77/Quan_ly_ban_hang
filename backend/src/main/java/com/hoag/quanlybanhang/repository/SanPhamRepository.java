package com.hoag.quanlybanhang.repository;

import com.hoag.quanlybanhang.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository quản lý sản phẩm
 * - findBySoLuongTonLessThanEqual: tìm sản phẩm có số lượng tồn kho <= ngưỡng
 *   (dùng để cảnh báo sản phẩm sắp hết hàng trên dashboard)
 */
@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {

    /**
     * Tìm tất cả sản phẩm có số lượng tồn kho nhỏ hơn hoặc bằng ngưỡng cho trước
     * Ví dụ: findBySoLuongTonLessThanEqual(10) → sản phẩm có tồn kho <= 10
     */
    List<SanPham> findBySoLuongTonLessThanEqual(int threshold);
}
