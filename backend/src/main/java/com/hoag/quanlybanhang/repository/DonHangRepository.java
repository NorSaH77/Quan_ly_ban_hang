package com.hoag.quanlybanhang.repository;

import com.hoag.quanlybanhang.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository quản lý đơn hàng
 * - JpaRepository cung cấp sẵn: findAll(), findById(), save(), deleteById(), count()...
 * - findByNgayTaoBetweenAndTrangThaiNot: tìm đơn hàng trong khoảng thời gian,
 *   loại trừ đơn có trạng thái nhất định (dùng cho thống kê doanh thu)
 */
@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {

    /**
     * Tìm tất cả đơn hàng được tạo trong khoảng [start, end]
     * và có trạng thái khác trangThai (vd: loại trừ 'DA_HUY')
     */
    List<DonHang> findByNgayTaoBetweenAndTrangThaiNot(
            LocalDateTime start, LocalDateTime end, String trangThai);
}
