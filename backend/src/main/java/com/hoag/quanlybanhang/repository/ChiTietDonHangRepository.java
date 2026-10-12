package com.hoag.quanlybanhang.repository;

import com.hoag.quanlybanhang.entity.ChiTietDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Order Detail (ChiTietDonHang) entity.
 */
@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Long> {
    // Additional query methods can be defined here if needed.
}

