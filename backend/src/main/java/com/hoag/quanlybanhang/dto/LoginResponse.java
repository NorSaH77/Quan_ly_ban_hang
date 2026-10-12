package com.hoag.quanlybanhang.dto;

/**
 * DTO for login response containing JWT token and basic user info.
 */
public class LoginResponse {
    private String token;
    private String email;
    private String hoTen;
    private String vaiTro;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
}

