package com.example.e_learningcourse.utils;

import android.util.Patterns;

public class ValidationUtils {
    public static String validateEmail(String email) {
        if (email == null) return null;
        if (email.trim().isEmpty()) return "Email is required";
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Invalid email format";
        return null;
    }

    public static String validatePassword(String password) {
        if (password == null) return null;
        if (password.trim().isEmpty()) return "Password cannot be empty";
        if (password.length() < 6) return "Password must be at least 6 characters";
        return null;
    }

    public static String validateConfirmPassword(String password, String confirmPassword) {
        if (confirmPassword == null) return null;
        if (confirmPassword.trim().isEmpty()) return "Confirm password cannot be empty";
        if (!confirmPassword.equals(password)) return "Passwords do not match";
        return null;
    }


    public static String validateOtp(String otp) {
        if (otp == null) return "OTP code cannot be empty";
        otp = otp.trim();

        if (otp.isEmpty()) return "OTP code cannot be empty";
        if (otp.length() != 6) return "OTP code must be exactly 6 digits";
        if (!otp.matches("\\d{6}")) return "OTP code must contain only numbers";

        return null;
    }
}
