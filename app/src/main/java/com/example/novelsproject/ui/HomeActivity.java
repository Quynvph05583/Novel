package com.example.novelsproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.novelsproject.R;


public class HomeActivity extends AppCompatActivity {
    ImageView imgTheloai, imgDanhsach;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgTheloai = findViewById(R.id.imgTheLoai);
        imgDanhsach = findViewById(R.id.imgDanhsach);
        imgTheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListCategoryActivity.class));
            }
        });
        imgDanhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListStoriesActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                Intent intentL = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intentL);
                Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
