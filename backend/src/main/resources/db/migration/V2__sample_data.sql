/* Flyway migration V2 - Sample data for testing */

-- Sample Products (Sản phẩm mẫu)
INSERT INTO san_pham (ma_san_pham, ten_san_pham, gia_ban, gia_von, so_luong_ton, danh_muc) VALUES
('SP001', 'iPhone 15 Pro Max 256GB', 29990000, 25000000, 10, 'Điện thoại'),
('SP002', 'Samsung Galaxy S24 Ultra', 27990000, 23000000, 8, 'Điện thoại'),
('SP003', 'MacBook Air M3 13 inch', 25990000, 21000000, 5, 'Laptop'),
('SP004', 'Dell XPS 13 9340', 22990000, 18500000, 7, 'Laptop'),
('SP005', 'iPad Air M2 11 inch', 14990000, 12000000, 12, 'Máy tính bảng'),
('SP006', 'Samsung Galaxy Tab S9', 13990000, 11200000, 9, 'Máy tính bảng'),
('SP007', 'Sony WH-1000XM5', 7990000, 5500000, 15, 'Tai nghe'),
('SP008', 'AirPods Pro 2', 5990000, 4200000, 20, 'Tai nghe'),
('SP009', 'Apple Watch Series 9', 9990000, 7500000, 11, 'Đồng hồ thông minh'),
('SP010', 'Samsung Galaxy Watch 6', 7490000, 5800000, 13, 'Đồng hồ thông minh'),
('SP011', 'Chuột Logitech MX Master 3S', 2490000, 1800000, 25, 'Phụ kiện'),
('SP012', 'Bàn phím Keychron K3', 1890000, 1300000, 30, 'Phụ kiện'),
('SP013', 'Ổ cứng SSD Samsung 990 Pro 2TB', 3990000, 3100000, 18, 'Lưu trữ'),
('SP014', 'Màn hình LG 27" 4K UHD', 8990000, 7200000, 6, 'Màn hình'),
('SP015', 'Loa Bluetooth JBL Charge 5', 3490000, 2500000, 22, 'Âm thanh');

-- Sample Customers (Khách hàng mẫu)
INSERT INTO khach_hang (ho_ten, so_dien_thoai, email, dia_chi) VALUES
('Nguyễn Văn An', '0901234567', 'an.nguyen@email.com', '123 Nguyễn Trãi, Quận 1, TP.HCM'),
('Trần Thị Bình', '0912345678', 'binh.tran@email.com', '456 Lê Lợi, Quận 3, TP.HCM'),
('Lê Hoàng Cường', '0923456789', 'cuong.le@email.com', '789 Võ Văn Tần, Quận 5, TP.HCM'),
('Phạm Minh Đức', '0934567890', 'duc.pham@email.com', '321 Cách Mạng Tháng 8, Quận 10, TP.HCM'),
('Hoàng Thị Lan', '0945678901', 'lan.hoang@email.com', '654 Đường 3/2, Quận 10, TP.HCM'),
('Vũ Quang Huy', '0956789012', 'huy.vu@email.com', '987 Nguyễn Đình Chiểu, Quận 3, TP.HCM'),
('Đặng Mỹ Linh', '0967890123', 'linh.dang@email.com', '147 Hai Bà Trưng, Quận 1, TP.HCM'),
('Bùi Thành Nam', '0978901234', 'nam.bui@email.com', '258 Pasteur, Quận 1, TP.HCM');

-- Sample Orders (Đơn hàng mẫu)
-- Đơn hàng 1: Nguyễn Văn An - iPhone 15 + AirPods
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024001', 1, 35980000, 'DA_GIAO', '2024-01-15 10:30:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(1, 1, 1, 29990000, 29990000),
(1, 8, 1, 5990000, 5990000);

-- Đơn hàng 2: Trần Thị Bình - MacBook + Chuột
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024002', 2, 28480000, 'DA_GIAO', '2024-01-18 14:20:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(2, 3, 1, 25990000, 25990000),
(2, 11, 1, 2490000, 2490000);

-- Đơn hàng 3: Lê Hoàng Cường - Samsung S24 + Galaxy Watch
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024003', 3, 35480000, 'DANG_GIAO', '2024-01-20 09:15:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(3, 2, 1, 27990000, 27990000),
(3, 10, 1, 7490000, 7490000);

-- Đơn hàng 4: Phạm Minh Đức - iPad + Bàn phím
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024004', 4, 16880000, 'CHO_XAC_NHAN', '2024-01-22 16:45:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(4, 5, 1, 14990000, 14990000),
(4, 12, 1, 1890000, 1890000);

-- Đơn hàng 5: Hoàng Thị Lan - Sony Headphone + Loa JBL
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024005', 5, 11480000, 'DA_HUY', '2024-01-25 11:00:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(5, 7, 1, 7990000, 7990000),
(5, 15, 1, 3490000, 3490000);

-- Đơn hàng 6: Vũ Quang Huy - Dell XPS + Màn hình LG
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024006', 6, 31980000, 'DA_GIAO', '2024-01-28 13:30:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(6, 4, 1, 22990000, 22990000),
(6, 14, 1, 8990000, 8990000);

-- Đơn hàng 7: Đặng Mỹ Linh - Apple Watch + SSD
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024007', 7, 13980000, 'DANG_GIAO', '2024-02-01 10:00:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(7, 9, 1, 9990000, 9990000),
(7, 13, 1, 3990000, 3990000);

-- Đơn hàng 8: Bùi Thành Nam - Galaxy Tab + Tai nghe
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024008', 8, 19980000, 'CHO_XAC_NHAN', '2024-02-03 15:20:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(8, 6, 1, 13990000, 13990000),
(8, 7, 1, 7990000, 7990000);

-- Đơn hàng 9: Nguyễn Văn An (mua lại) - SSD + Chuột
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024009', 1, 6480000, 'DA_GIAO', '2024-02-05 12:10:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(9, 13, 1, 3990000, 3990000),
(9, 11, 1, 2490000, 2490000);

-- Đơn hàng 10: Trần Thị Bình (mua lại) - AirPods
INSERT INTO don_hang (ma_don_hang, khach_hang_id, tong_tien, trang_thai, ngay_tao) VALUES
('DH2024010', 2, 5990000, 'DA_GIAO', '2024-02-07 17:00:00');
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES
(10, 8, 1, 5990000, 5990000);