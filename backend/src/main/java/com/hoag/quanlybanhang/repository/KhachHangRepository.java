package com.hoag.quanlybanhang.repository;

import com.hoag.quanlybanhang.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    Optional<KhachHang> findByEmail(String email);
}

