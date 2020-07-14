package com.example.novelsproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novelsproject.R;
import com.example.novelsproject.db.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {
    static ConnectionDB connectionDB = new ConnectionDB();
    static Connection con = connectionDB.CONN();
    EditText edUser, edName, edEmail, edPass, edRePass;
    Boolean isAdmin;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterActivity.CheckRegister().execute("");
            }
        });

    }

    public class CheckRegister extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            edUser.setText("");
            edName.setText("");
            edEmail.setText("");
            edPass.setText("");
            edRePass.setText("");
//            Toast.makeText(RegisterActivity.this, r, Toast.LENGTH_SHORT).show();
//            if (isSuccess) {
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String user = edUser.getText().toString();
            String name = edName.getText().toString();
            String email = edEmail.getText().toString();
            String pass = edPass.getText().toString();
            String repass = edRePass.getText().toString();
            if (user.isEmpty()) {
                z = "Vui lòng nhập Tài khoản";
            }else if (name.isEmpty()) {
                z = "Vui lòng nhập Họ và tên";
            } else if (email.isEmpty()) {
                z = "Vui lòng nhập Email";
            } else if (pass.isEmpty()) {
                z = "Vui lòng nhập Mật khẩu";
            } else if (repass.isEmpty()) {
                z = "Vui lòng nhập lại Mật khẩu";
            } else if (pass.equals(pass)) {
                z = "Mật khẩu không trùng khớp";
            }
            else {
                try {
                    if (con == null) {
                        z = "Đăng nhập không thành công. Kiểm tra kết nối mạng!";
                    } else {
                        String query = "INSERT INTO Users (UserId,Username,Pass, Email, isAdmin) VALUES ('"+edUser.getText()+"','"+edName.getText()+"','"+edEmail.getText()+"','"+edRePass.getText()+"', )";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            isSuccess = true;
                            z = "Đăng kí thành công";
                            con.close();
                        } else {
                            z = "Đăng kí thất bại";
                            isSuccess = false;
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    public void init() {
        edUser = (EditText) findViewById(R.id.edUser);
        edName = (EditText) findViewById(R.id.edName);
        edPass = (EditText) findViewById(R.id.edPass);
        edRePass = (EditText) findViewById(R.id.edRePass);
        edEmail = (EditText) findViewById(R.id.edEmail);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }
}
