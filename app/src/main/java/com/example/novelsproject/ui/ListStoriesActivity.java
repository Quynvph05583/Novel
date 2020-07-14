package com.example.novelsproject.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.novelsproject.Model.Stories;
import com.example.novelsproject.R;
import com.example.novelsproject.dao.StoriesDao;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class ListStoriesActivity extends AppCompatActivity {
    private StoriesAdapter storiesAdapter;
    GridView GridView;
    SearchView edsearch;
    private String category_id;
    private boolean success = false;
    private List<Stories> listStories;
    Toolbar toolbar;
    StoriesAdapter.ViewHolder holder;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_stories);

        GridView = (GridView) findViewById(R.id.gridView);
        toolbar = findViewById(R.id.toolbarStories);
        edsearch = (SearchView) findViewById(R.id.edsearch);

        toolbar.setTitle("Danh sách truyện");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            category_id = bundle.getString("category_id");
            listStories = StoriesDao.getComicByCategoryId(category_id);
        } else {
            listStories = StoriesDao.getAllStories();
        }

//        Toast.makeText(ListStoriesActivity.this, category_id, Toast.LENGTH_LONG).show();
        SyncData orderData = new SyncData();
        orderData.execute("");
        setClick();

        edsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                storiesAdapter.filter(text, listStories);
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setClick() {
        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListStoriesActivity.this, ChapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("story_id", listStories.get(position).getComicId());
                bundle.putString("thumbnail", listStories.get(position).getThumbnail());
                bundle.putString("name", listStories.get(position).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Loading...";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ListStoriesActivity.this, "Đang đồng bộ",
                    "Đang tải danh sách Truyện! Vui lòng đợi...", true);
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
            Toast.makeText(ListStoriesActivity.this, msg + "", Toast.LENGTH_LONG).show();
            try {
                storiesAdapter = new StoriesAdapter(listStories, ListStoriesActivity.this);
                GridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
                GridView.setAdapter(storiesAdapter);
            } catch (Exception ex) {
            }
        }
    }

    public class StoriesAdapter extends BaseAdapter {

        public class ViewHolder {
            TextView tvName, views;
            ImageView imageView;
        }

        public List<Stories> parkingList;
        public Context context;
        ArrayList<Stories> arrayList;

        private StoriesAdapter(List<Stories> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arrayList = new ArrayList<Stories>();
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

        @SuppressLint("WrongViewCast")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.lv_story, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
                viewHolder.tvName = (TextView) rowView.findViewById(R.id.tvName);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Picasso.with(context).load(parkingList.get(position).getThumbnail()).into(viewHolder.imageView);
            viewHolder.tvName.setText("Truyện: " + parkingList.get(position).getName() + "");
            return rowView;
        }

        public void filter(String charText, List<Stories> listStories) {
            charText = charText.toLowerCase(Locale.getDefault());

            parkingList.clear();
            if (category_id != null) {
                if (charText.length() == 0) {
                    parkingList.addAll(StoriesDao.getComicByCategoryId(category_id));
                } else {
                    for (Stories st : StoriesDao.getComicByCategoryId(category_id)) {
                        if (st.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                            parkingList.add(st);
                        }
                    }
                }
            } else {
                if (charText.length() == 0) {
                    parkingList.addAll(StoriesDao.getAllStories());
                } else {
                    for (Stories st : StoriesDao.getAllStories()) {
                        if (st.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                            parkingList.add(st);
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

}
