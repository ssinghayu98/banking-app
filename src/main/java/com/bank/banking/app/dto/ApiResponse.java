package com.bank.banking.app.dto;

public class ApiResponse<T> {

    private String message;
    private T data;

    // ✅ Default constructor (needed for Jackson)
    public ApiResponse() {
    }

    // ✅ All args constructor
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // ✅ Static success helper (clean usage)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("SUCCESS", data);
    }

    // ✅ Static error helper
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null);
    }

    // ---------------- GETTERS ----------------
    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    // ---------------- SETTERS ----------------
    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}