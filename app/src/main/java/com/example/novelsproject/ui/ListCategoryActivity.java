package com.example.novelsproject.ui;


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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.novelsproject.Model.Category;
import com.example.novelsproject.R;
import com.example.novelsproject.dao.CategoryDao;
import com.example.novelsproject.db.ConnectionDB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class ListCategoryActivity extends AppCompatActivity {
    private CategoryAdapter categoryAdapter;
    ListView listView;
    private List<Category> listCategory;
    private boolean success = false;
    private ConnectionDB connectionDB;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_categorys);


        listView = (ListView) findViewById(R.id.listCategory);
        toolbar = findViewById(R.id.toolbarCategory);
        connectionDB = new ConnectionDB();
        listCategory = CategoryDao.getListCategory();
        SyncData orderData = new SyncData();
        orderData.execute("");
        setClick();


        toolbar.setTitle("Thể loại");
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

    private void setClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListCategoryActivity.this, ListStoriesActivity.class);
                Bundle bundle = new Bundle();
                Category obj = (Category) listView.getSelectedItem();
                bundle.putString("category_id", listCategory.get(position).getCategoryID());
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
            progress = ProgressDialog.show(ListCategoryActivity.this, "Đang đồng bộ",
                    "Đang tải danh sách Thể loại! Vui lòng đợi...", true);
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
            Toast.makeText(ListCategoryActivity.this, "", Toast.LENGTH_SHORT).show();
            try {
                categoryAdapter = new CategoryAdapter(listCategory, ListCategoryActivity.this);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(categoryAdapter);
            } catch (Exception ex) {
            }
        }
    }

    public class CategoryAdapter extends BaseAdapter {
        public class ViewHolder {
            TextView tvName;
        }

        public List<Category> parkingList;
        public Context context;
        ArrayList<Category> arrayList;

        private CategoryAdapter(List<Category> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arrayList = new ArrayList<Category>();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.lv_category, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) rowView.findViewById(R.id.tvName);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvName.setText(parkingList.get(position).getName() + "");
            return rowView;
        }
    }
}
