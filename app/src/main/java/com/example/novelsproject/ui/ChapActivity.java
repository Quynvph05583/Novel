package com.example.novelsproject.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelsproject.Model.StoriesDetail;
import com.example.novelsproject.R;
import com.example.novelsproject.dao.StoriesDetailDao;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;


public class ChapActivity extends AppCompatActivity implements Html.ImageGetter{
    private ChapAdapter chapAdapter;
    ListView listView;
    TextView imageViewChap;
    TextView tvName;

    private boolean success = false;
    Toolbar toolbar;
    private String story_id;
    private String thumbnail;
    private String name;
    private List<StoriesDetail> listChapter;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chap);

        listView = (ListView) findViewById(R.id.listChap);
        toolbar = findViewById(R.id.toolbarChap);

        toolbar.setTitle("Chương");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        story_id = bundle.getString("story_id");
        thumbnail = bundle.getString("thumbnail");
        name = bundle.getString("name");

        Spanned spanned = Html.fromHtml(thumbnail, this, null);
        imageViewChap = (TextView) findViewById(R.id.imageViewChap);
        imageViewChap.setText(spanned);

        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(name);

        listChapter = StoriesDetailDao.getChapterByComicId(story_id);
//        Toast.makeText(ChapActivity.this, story_id, Toast.LENGTH_SHORT).show();
        SyncData orderData = new SyncData();
        orderData.execute("");
        setClick();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChapActivity.this, DoctruyenActivity.class);
                Bundle bundle = new Bundle();
                StoriesDetail obj = (StoriesDetail) listView.getSelectedItem();
                bundle.putString("comicDetail_id", listChapter.get(position).getComicDetailID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
                mDrawable.setBounds(0, 0, 800, 1280);
                mDrawable.setLevel(1);
                CharSequence t = imageViewChap.getText();
                imageViewChap.setText(t);
            }
        }
    }

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Loading...";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ChapActivity.this, "Đang đồng bộ",
                    "Đang tải danh sách Chương! Vui lòng đợi...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            progress.dismiss();
            Toast.makeText(ChapActivity.this, msg + "", Toast.LENGTH_LONG).show();
            try {
                chapAdapter = new ChapAdapter(listChapter, ChapActivity.this);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(chapAdapter);
            } catch (Exception ex) {
            }
        }
    }

    private class ChapAdapter extends BaseAdapter {

        public class ViewHolder {
            TextView tvChap, tvTitle;
        }

        public List<StoriesDetail> parkingList;
        public Context context;
        ArrayList<StoriesDetail> arrayList;

        private ChapAdapter(List<StoriesDetail> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arrayList = new ArrayList<StoriesDetail>();
            arrayList.addAll(parkingList);
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.lv_chap, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvChap = (TextView) rowView.findViewById(R.id.tvChap);
                viewHolder.tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvChap.setText("Chương: " + parkingList.get(position).getChapters() + "");
            viewHolder.tvTitle.setText("Nội dung: " + parkingList.get(position).getTitle() + "");
            return rowView;
        }
    }
}
