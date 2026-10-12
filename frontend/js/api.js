/* ============================================
   MODULE API DÙNG CHUNG
   Mô tả: Xử lý gọi API, xác thực, thông báo
   ============================================ */

// Địa chỉ gốc của API backend
const API_URL = 'http://localhost:8080/api';

// --- Quản lý Token JWT ---

/** Lấy token từ localStorage */
function getToken() {
  return localStorage.getItem('token');
}

/** Lưu token vào localStorage */
function setToken(token) {
  localStorage.setItem('token', token);
}

/** Xóa token (khi đăng xuất) */
function removeToken() {
  localStorage.removeItem('token');
}

/** Kiểm tra đã đăng nhập chưa */
function isLoggedIn() {
  return !!getToken();
}

/** Kiểm tra xác thực - chuyển hướng nếu chưa đăng nhập */
function checkAuth() {
  if (!isLoggedIn()) {
    window.location.href = 'dang-nhap.html';
  }
}

// --- Hàm gọi API chung ---

/**
 * Gọi API với xác thực tự động
 * @param {string} endpoint - Đường dẫn API (ví dụ: '/san-pham')
 * @param {object} options - Tùy chọn fetch (method, body, headers...)
 * @returns {Promise<object>} Dữ liệu JSON từ server
 */
async function apiCall(endpoint, options = {}) {
  // Tạo headers mặc định với Content-Type JSON
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  };

  // Thêm token xác thực nếu có
  const token = getToken();
  if (token) {
    headers['Authorization'] = 'Bearer ' + token;
  }

  try {
    const res = await fetch(API_URL + endpoint, {
      ...options,
      headers,
    });

    // Nếu bị từ chối quyền truy cập → đăng xuất
    if (res.status === 401 || res.status === 403) {
      removeToken();
      window.location.href = 'dang-nhap.html';
      return null;
    }

    // Phân tích kết quả JSON
    const data = await res.json();
    return data;
  } catch (error) {
    console.error('Lỗi khi gọi API:', error);
    showToast('Không thể kết nối đến máy chủ!', 'error');
    return null;
  }
}

/**
 * Gọi API phương thức GET
 * @param {string} endpoint - Đường dẫn API
 */
async function apiGet(endpoint) {
  return apiCall(endpoint);
}

/**
 * Gọi API phương thức POST (tạo mới)
 * @param {string} endpoint - Đường dẫn API
 * @param {object} body - Dữ liệu gửi đi
 */
async function apiPost(endpoint, body) {
  return apiCall(endpoint, {
    method: 'POST',
    body: JSON.stringify(body),
  });
}

/**
 * Gọi API phương thức PUT (cập nhật)
 * @param {string} endpoint - Đường dẫn API
 * @param {object} body - Dữ liệu gửi đi
 */
async function apiPut(endpoint, body) {
  return apiCall(endpoint, {
    method: 'PUT',
    body: JSON.stringify(body),
  });
}

/**
 * Gọi API phương thức DELETE (xóa)
 * @param {string} endpoint - Đường dẫn API
 */
async function apiDelete(endpoint) {
  return apiCall(endpoint, {
    method: 'DELETE',
  });
}

// --- Thông báo Toast ---

/**
 * Hiển thị thông báo dạng toast ở góc trên phải
 * @param {string} msg - Nội dung thông báo
 * @param {string} type - Loại: 'success', 'error', 'warning', 'info'
 */
function showToast(msg, type = 'success') {
  // Tạo container nếu chưa có
  let container = document.querySelector('.toast-container');
  if (!container) {
    container = document.createElement('div');
    container.className = 'toast-container';
    document.body.appendChild(container);
  }

  // Chọn icon phù hợp với loại thông báo
  const icons = {
    success: '✅',
    error: '❌',
    warning: '⚠️',
    info: 'ℹ️',
  };

  // Tạo phần tử toast
  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  toast.innerHTML = `<span>${icons[type] || ''}</span><span>${msg}</span>`;
  container.appendChild(toast);

  // Tự động xóa sau 3 giây
  setTimeout(() => {
    toast.style.animation = 'slideOut 0.3s ease forwards';
    setTimeout(() => {
      toast.remove();
      // Xóa container nếu không còn toast nào
      if (container.children.length === 0) {
        container.remove();
      }
    }, 300);
  }, 3000);
}

// --- Đăng xuất ---

/** Đăng xuất: xóa token và chuyển về trang đăng nhập */
function logout() {
  removeToken();
  window.location.href = 'dang-nhap.html';
}

// --- Tiện ích định dạng ---

/**
 * Định dạng số tiền theo kiểu Việt Nam
 * @param {number} amount - Số tiền cần định dạng
 * @returns {string} Chuỗi tiền đã định dạng (ví dụ: "1.500.000 đ")
 */
function formatMoney(amount) {
  if (amount == null || isNaN(amount)) return '0 đ';
  return Number(amount).toLocaleString('vi-VN') + ' đ';
}

/**
 * Định dạng ngày giờ theo kiểu Việt Nam
 * @param {string} dateStr - Chuỗi ngày giờ ISO
 * @returns {string} Ngày giờ đã định dạng
 */
function formatDate(dateStr) {
  if (!dateStr) return '';
  const d = new Date(dateStr);
  return d.toLocaleDateString('vi-VN') + ' ' + d.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' });
}

// --- Quản lý Modal ---

/**
 * Mở modal bằng ID
 * @param {string} modalId - ID của phần tử modal overlay
 */
function openModal(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) modal.classList.add('active');
}

/**
 * Đóng modal bằng ID
 * @param {string} modalId - ID của phần tử modal overlay
 */
function closeModal(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) modal.classList.remove('active');
}
