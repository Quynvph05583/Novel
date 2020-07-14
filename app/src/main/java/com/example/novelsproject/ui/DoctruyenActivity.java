package com.example.novelsproject.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelsproject.Model.StoriesDetail;
import com.example.novelsproject.R;
import com.example.novelsproject.dao.StoriesDetailDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DoctruyenActivity extends AppCompatActivity implements Html.ImageGetter{
    Toolbar toolbar;
    private String comicDetail_id;
    private List<StoriesDetail> listDoctruyen;
    TextView htmlText;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_doctruyen);

        toolbar = findViewById(R.id.toolbarRead);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        comicDetail_id = bundle.getString("comicDetail_id");
        listDoctruyen = StoriesDetailDao.getTruyenByComicDetailId(comicDetail_id);
//        Toast.makeText(DoctruyenActivity.this, comicDetail_id, Toast.LENGTH_SHORT).show();
        Spanned spanned = Html.fromHtml(listDoctruyen.get(0).getContent(), this, null);
        htmlText = (TextView) findViewById(R.id.htmlText);
        htmlText.setText(spanned);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        new LoadImage().execute(source, d);
        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                int width = htmlText.getWidth() < bitmap.getWidth() ? htmlText.getWidth() : bitmap.getWidth();
                int height = bitmap.getHeight() * width / bitmap.getWidth();
                mDrawable.setBounds(0, 0, width, height);
                mDrawable.setLevel(1);
                CharSequence t = htmlText.getText();
                htmlText.setText(t);
            }
        }
    }
}
