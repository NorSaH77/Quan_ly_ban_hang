package com.hoag.quanlybanhang.service;

import com.hoag.quanlybanhang.entity.KhachHang;
import com.hoag.quanlybanhang.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service xử lý logic nghiệp vụ cho Khách hàng
 * Bao gồm: lấy danh sách, tìm theo ID, tạo mới, cập nhật
 */
@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    /**
     * Lấy danh sách tất cả khách hàng
     */
    public List<KhachHang> getAll() {
        return khachHangRepository.findAll();
    }

    /**
     * Tìm khách hàng theo ID, ném lỗi nếu không tìm thấy
     */
    public KhachHang getById(Long id) {
        return khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với ID: " + id));
    }

    /**
     * Tạo khách hàng mới
     */
    public KhachHang create(KhachHang kh) {
        return khachHangRepository.save(kh);
    }

    /**
     * Cập nhật thông tin khách hàng theo ID
     */
    public KhachHang update(Long id, KhachHang kh) {
        // Tìm khách hàng hiện tại trong DB
        KhachHang existing = getById(id);

        // Cập nhật từng trường
        existing.setHoTen(kh.getHoTen());
        existing.setSoDienThoai(kh.getSoDienThoai());
        existing.setEmail(kh.getEmail());
        existing.setDiaChi(kh.getDiaChi());

        // Lưu và trả về khách hàng đã cập nhật
        return khachHangRepository.save(existing);
    }
}
