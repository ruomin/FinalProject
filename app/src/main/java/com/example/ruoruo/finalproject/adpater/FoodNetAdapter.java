package com.example.ruoruo.finalproject.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruoruo.finalproject.R;
import com.example.ruoruo.finalproject.bean.Food;

import java.util.List;


public class FoodNetAdapter extends BaseAdapter {


    private Context context;
    private List<Food> datas;
    private LayoutInflater layoutInflater;

    public FoodNetAdapter(Context context, List<Food> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_food, null);
            holder.foodId = (TextView) view.findViewById(R.id.foodId);
            holder.label = (TextView) view.findViewById(R.id.label);
            holder.nutrients = (TextView) view.findViewById(R.id.nutrients);
            holder.category = (TextView) view.findViewById(R.id.category);
            holder.categoryLabel = (TextView) view.findViewById(R.id.categoryLabel);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Food food = datas.get(i);
        System.out.println(food);
        holder.foodId.setText(food.getFoodId() + "");
        holder.label.setText(food.getLabel() + "");
        holder.nutrients.setText(food.getNutrients().toString() + "");
        holder.category.setText(food.getCategory() + "");
        holder.categoryLabel.setText(food.getCategoryLabel() + "");
        return view;
    }

    class ViewHolder {
        public TextView foodId;
        public TextView label;
        public TextView nutrients;
        public TextView category;
        public TextView categoryLabel;
    }
}


