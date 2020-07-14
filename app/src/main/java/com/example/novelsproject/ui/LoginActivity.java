package com.example.novelsproject.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novelsproject.R;
import com.example.novelsproject.db.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    public static final String filename = "UserLogin";
    EditText eduser, edpass;
    Button btnLogin;
    CheckBox chkRemember;
    static ConnectionDB connectionDB = new ConnectionDB();
    static Connection con = connectionDB.CONN();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin isLogin = new CheckLogin();
                isLogin.execute("");
            }
        });

    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(LoginActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            SharedPreferences sharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String usernam = eduser.getText().toString();
            String passwordd = edpass.getText().toString();
            if (usernam.isEmpty()) {
                z = "Vui lòng nhập Tài khoản";
            } else if (passwordd.isEmpty()) {
                z = "Vui lòng nhập Mật khẩu";
            } else {
                try {
                    if (con == null) {
                        z = "Đăng nhập không thành công. Kiểm tra kết nối mạng!";
                    } else {
                        String query = "select * from Users where userId= '" + usernam.toString() + "' and pass = '" + passwordd.toString() + "'  ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            editor.putString("eduser", usernam);
                            editor.putString("edpass", passwordd);
                            editor.putBoolean("chk", chkRemember.isChecked());
                            editor.commit();
                            isSuccess = true;
                            z = "Đăng nhập thành công";
                        } else {
                            z = "Tài khoản hoặc mật khẩu không đúng";
                            isSuccess = false;
                            edpass.getText().clear();
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
        eduser = (EditText) findViewById(R.id.eduser);
        edpass = (EditText) findViewById(R.id.edpass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        chkRemember = (CheckBox) findViewById(R.id.checkRemember);
    }

    @Override
    public void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        Boolean chk = sharedPreferences.getBoolean("chk", false);

        if (chk) {
            String user = sharedPreferences.getString("eduser", "");
            String pass = sharedPreferences.getString("edpass", "");
            eduser.setText(user);
            edpass.setText(pass);
        }
        chkRemember.setChecked(chk);
        super.onResume();
    }
}
