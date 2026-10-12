package com.hoag.quanlybanhang.service;

import com.hoag.quanlybanhang.dto.TaoDonHangRequest;
import com.hoag.quanlybanhang.entity.ChiTietDonHang;
import com.hoag.quanlybanhang.entity.DonHang;
import com.hoag.quanlybanhang.entity.KhachHang;
import com.hoag.quanlybanhang.entity.SanPham;
import com.hoag.quanlybanhang.repository.ChiTietDonHangRepository;
import com.hoag.quanlybanhang.repository.DonHangRepository;
import com.hoag.quanlybanhang.repository.KhachHangRepository;
import com.hoag.quanlybanhang.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý logic nghiệp vụ cho Đơn hàng
 * Bao gồm: lấy danh sách, tìm theo ID, tạo đơn hàng mới, cập nhật trạng thái
 */
@Service
public class DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    /**
     * Lấy danh sách tất cả đơn hàng
     */
    public List<DonHang> getAll() {
        return donHangRepository.findAll();
    }

    /**
     * Tìm đơn hàng theo ID, ném lỗi nếu không tìm thấy
     */
    public DonHang getById(Long id) {
        return donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
    }

    /**
     * Tạo đơn hàng mới - quy trình:
     * 1. Sinh mã đơn hàng tự động (DH + timestamp)
     * 2. Tìm khách hàng theo ID
     * 3. Duyệt qua từng chi tiết: kiểm tra tồn kho, trừ kho, tính tạm tính
     * 4. Tính tổng tiền, đặt trạng thái ban đầu, lưu vào DB
     *
     * @Transactional đảm bảo nếu có lỗi giữa chừng, tất cả sẽ được rollback
     */
    @Transactional
    public DonHang create(TaoDonHangRequest req) {
        // Bước 1: Tạo đơn hàng với mã tự sinh
        DonHang donHang = new DonHang();
        donHang.setMaDonHang("DH" + System.currentTimeMillis());

        // Bước 2: Tìm khách hàng
        KhachHang khachHang = khachHangRepository.findById(req.getKhachHangId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khách hàng với ID: " + req.getKhachHangId()));
        donHang.setKhachHang(khachHang);

        // Bước 3: Thiết lập trạng thái và thời gian
        donHang.setTrangThai("CHO_XAC_NHAN");
        donHang.setNgayTao(LocalDateTime.now());

        // Lưu đơn hàng trước để có ID cho chi tiết đơn hàng
        donHang = donHangRepository.save(donHang);

        // Bước 4: Xử lý từng chi tiết đơn hàng
        double tongTien = 0;
        List<ChiTietDonHang> danhSachChiTiet = new ArrayList<>();

        for (TaoDonHangRequest.ChiTietDto ct : req.getChiTiet()) {
            // Tìm sản phẩm
            SanPham sanPham = sanPhamRepository.findById(ct.getSanPhamId())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy sản phẩm với ID: " + ct.getSanPhamId()));

            // Kiểm tra tồn kho đủ không
            if (sanPham.getSoLuongTon() < ct.getSoLuong()) {
                throw new RuntimeException(
                        "Sản phẩm '" + sanPham.getTenSanPham() + "' không đủ tồn kho. " +
                        "Còn lại: " + sanPham.getSoLuongTon() + ", yêu cầu: " + ct.getSoLuong());
            }

            // Trừ tồn kho
            sanPham.setSoLuongTon(sanPham.getSoLuongTon() - ct.getSoLuong());
            sanPhamRepository.save(sanPham);

            // Tạo chi tiết đơn hàng
            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setDonHang(donHang);
            chiTiet.setSanPham(sanPham);
            chiTiet.setSoLuong(ct.getSoLuong());
            chiTiet.setGiaBan(sanPham.getGiaBan());  // Lấy giá bán từ sản phẩm
            chiTiet.setTamTinh(sanPham.getGiaBan() * ct.getSoLuong()); // tạm tính = giá × số lượng

            danhSachChiTiet.add(chiTiet);
            tongTien += chiTiet.getTamTinh();
        }

        // Lưu tất cả chi tiết đơn hàng
        chiTietDonHangRepository.saveAll(danhSachChiTiet);

        // Bước 5: Cập nhật tổng tiền cho đơn hàng
        donHang.setTongTien(tongTien);
        return donHangRepository.save(donHang);
    }

    /**
     * Cập nhật trạng thái đơn hàng
     * Các trạng thái: CHO_XAC_NHAN → DA_XAC_NHAN → DANG_GIAO → DA_GIAO hoặc DA_HUY
     */
    public DonHang capNhatTrangThai(Long id, String trangThai) {
        DonHang donHang = getById(id);
        donHang.setTrangThai(trangThai);
        return donHangRepository.save(donHang);
    }
}
