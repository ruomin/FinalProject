package com.example.ruoruo.finalproject;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ruoruo.finalproject.bean.FoodLocal;
import com.example.ruoruo.finalproject.util.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FoodTotalFragment extends Fragment {

    private TextView total;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_food_total, container, false); //
        initView(view);
        return view;
    }


    private void initView(View view) {
        dbHelper = new DBHelper(getActivity());
        total = (TextView) view.findViewById(R.id.total);
        List<FoodLocal> datas = new ArrayList<FoodLocal>();
        Map<String, List<FoodLocal>> map = new HashMap<String, List<FoodLocal>>();
        Cursor c = dbHelper.query();
        while (c.moveToNext()) {
            String foodId = c.getString(c.getColumnIndex("foodId"));
            String uri = c.getString(c.getColumnIndex("url"));
            String label = c.getString(c.getColumnIndex("label"));
            String nutrients = c.getString(c.getColumnIndex("nutrients"));
            String category = c.getString(c.getColumnIndex("category"));
            String categoryLabel = c.getString(c.getColumnIndex("categoryLabel"));
            String tagStr = c.getString(c.getColumnIndex("tag"));

            //store the data from the result
            FoodLocal p = new FoodLocal(foodId, uri, label, nutrients, category, categoryLabel, tagStr);
            datas.add(p);
        }

        for (FoodLocal f : datas) {
            if (map.containsKey(f.getTag())) {
                List<FoodLocal> list = map.get(f.getTag());
                list.add(f);
                map.put(f.getTag(), list);
            } else {
                List<FoodLocal> list = new ArrayList<FoodLocal>();
                list.add(f);
                map.put(f.getTag(), list);
            }
        }


        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        String resultStr = "";
        while (iterator.hasNext()) {
            String key = iterator.next();
            List<FoodLocal> foodLocalList = map.get(key);
            for (int i = 0; i < foodLocalList.size() - 1; i++) {// 外层循环控制排序趟数
                for (int j = 0; j < foodLocalList.size() - 1 - i; j++) {// 内层循环控制每一趟排序多少次
                    FoodLocal f1 = foodLocalList.get(j);
                    FoodLocal f2 = foodLocalList.get(j + 1);
                    float c1 = Float.parseFloat(f1.getNutrients().split(",")[0].split(":")[1].trim());
                    float c2 = Float.parseFloat(f2.getNutrients().split(",")[0].split(":")[1].trim());
                    if (c1 > c2) {
                        FoodLocal temp = foodLocalList.get(j);
                        foodLocalList.set(j, foodLocalList.get(j + 1));
                        foodLocalList.set(j + 1, temp);
                    }
                }
            }

            resultStr += "tag=" + key + "   min:" + foodLocalList.get(0).getNutrients().split(",")[0].split(":")[1].trim()
                    + "  max:" + foodLocalList.get(foodLocalList.size() - 1).getNutrients().split(",")[0].split(":")[1].trim() + "   ";
            float sum = 0.0f;
            for (FoodLocal f : foodLocalList) {
                sum += Float.parseFloat(f.getNutrients().split(",")[0].split(":")[1].trim());
            }
            float avg = sum / foodLocalList.size();

            resultStr += "total:" + sum + "   avg:" + avg + "\n\n";
        }

        total.setText(resultStr);
    }
}
