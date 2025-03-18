package com.example.foodorderapp; // Đảm bảo tên package khớp với project của bạn

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.edtEmail);
        etPassword = findViewById(R.id.edtPassword);
        etConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Kiểm tra các trường nhập
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Vui lòng nhập email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Vui lòng nhập mật khẩu");
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu nhập lại không khớp");
            return;
        }

        // Đăng ký tài khoản với Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng ký thành công
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                        // Chuyển sang màn hình đăng nhập
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        // Đăng ký thất bại
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
