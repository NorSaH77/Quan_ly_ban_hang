package com.hoag.quanlybanhang.service;

import com.hoag.quanlybanhang.entity.SanPham;
import com.hoag.quanlybanhang.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service xử lý logic nghiệp vụ cho Sản phẩm
 * Bao gồm: CRUD cơ bản + nhập kho (cộng thêm số lượng tồn)
 */
@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    /**
     * Lấy danh sách tất cả sản phẩm
     */
    public List<SanPham> getAll() {
        return sanPhamRepository.findAll();
    }

    /**
     * Tìm sản phẩm theo ID, ném lỗi nếu không tìm thấy
     */
    public SanPham getById(Long id) {
        return sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
    }

    /**
     * Tạo sản phẩm mới
     */
    public SanPham create(SanPham sp) {
        return sanPhamRepository.save(sp);
    }

    /**
     * Cập nhật thông tin sản phẩm theo ID
     * - Tìm sản phẩm cũ trong DB
     * - Ghi đè các trường bằng dữ liệu mới
     * - Lưu lại vào DB
     */
    public SanPham update(Long id, SanPham sp) {
        // Tìm sản phẩm hiện tại trong DB
        SanPham existing = getById(id);

        // Cập nhật từng trường
        existing.setMaSanPham(sp.getMaSanPham());
        existing.setTenSanPham(sp.getTenSanPham());
        existing.setGiaBan(sp.getGiaBan());
        existing.setGiaVon(sp.getGiaVon());
        existing.setSoLuongTon(sp.getSoLuongTon());
        existing.setDanhMuc(sp.getDanhMuc());

        // Lưu và trả về sản phẩm đã cập nhật
        return sanPhamRepository.save(existing);
    }

    /**
     * Xóa sản phẩm theo ID
     */
    public void delete(Long id) {
        sanPhamRepository.deleteById(id);
    }

    /**
     * Nhập kho: cộng thêm số lượng vào tồn kho hiện tại
     * Ví dụ: tồn kho = 5, nhập thêm 10 → tồn kho = 15
     */
    public SanPham nhapKho(Long sanPhamId, int soLuong) {
        SanPham sp = getById(sanPhamId);

        // Cộng thêm số lượng nhập vào tồn kho hiện tại
        sp.setSoLuongTon(sp.getSoLuongTon() + soLuong);

        return sanPhamRepository.save(sp);
    }
}
