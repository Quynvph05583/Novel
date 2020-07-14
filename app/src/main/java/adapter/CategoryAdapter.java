package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.novelsproject.Model.Category;
import com.example.novelsproject.R;
import com.example.novelsproject.dao.CategoryDao;
import com.example.novelsproject.ui.ListCategoryActivity;

import java.util.ArrayList;
import java.util.List;

//public class CategoryAdapter extends BaseAdapter {
//    private Context context;
//    private List<Category> listCategory;
//    CategoryDao categoryDao;
//
//    public CategoryAdapter(Context context, List<Category> listCategory) {
//        this.context = context;
//        this.listCategory = listCategory;
//        categoryDao = new CategoryDao(context);
//    }
//    public class ViewHolder {
//        TextView tvName;
//    }
//    @Override
//    public int getCount() {
//        return listCategory.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listCategory.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View rowView = convertView;
//        ViewHolder viewHolder= null;
//        if (rowView == null)
//        {
//            LayoutInflater inflater =
//            rowView = inflater.inflate(R.layout.lv_category, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.tvName = (TextView) rowView.findViewById(R.id.tvName);
//            rowView.setTag(viewHolder);
//        }
//        else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.tvName.setText(parkingList.get(position).getName()+"");
//        return rowView;
//    }
//}
