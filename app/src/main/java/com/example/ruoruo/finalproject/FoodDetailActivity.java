package com.example.ruoruo.finalproject;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruoruo.finalproject.bean.FoodLocal;
import com.example.ruoruo.finalproject.util.DBHelper;

public class FoodDetailActivity extends AppCompatActivity {
    private TextView foodId;
    private TextView label;
    private TextView nutrients;
    private TextView category;
    private TextView categoryLabel;
    private EditText tag;
    private Button addTag;
    private Button delete;
    FoodLocal food = null;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        dbHelper = new DBHelper(this);
        foodId = (TextView) findViewById(R.id.foodId);
        label = (TextView) findViewById(R.id.label);
        nutrients = (TextView) findViewById(R.id.nutrients);
        category = (TextView) findViewById(R.id.category);
        categoryLabel = (TextView) findViewById(R.id.categoryLabel);
        tag = (EditText) findViewById(R.id.tag);
        addTag = (Button) findViewById(R.id.addTag);
        delete = (Button) findViewById(R.id.delete);
        food = (FoodLocal) getIntent().getSerializableExtra("data");


        foodId.setText(food.getFoodId() + "");
        label.setText(food.getLabel() + "");
        nutrients.setText(food.getNutrients() + "");
        category.setText(food.getCategory() + "");
        categoryLabel.setText(food.getCategoryLabel() + "");
        tag.setText(food.getTag() + "");

        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                String tagStr = tag.getText().toString().trim();
                if (tagStr.equals("")) {
                    Toast.makeText(FoodDetailActivity.this, "Input can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                values.put("tag", tagStr);
                dbHelper.update(values, "foodId=?", new String[]{food.getFoodId()});
                Toast.makeText(FoodDetailActivity.this, "Update Success", Toast.LENGTH_LONG).show();
                FoodDetailActivity.this.finish();
            }
        });


        /**
         * delete food
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.delete(food.getFoodId());
                Toast.makeText(FoodDetailActivity.this, "Delete Success", Toast.LENGTH_LONG).show();
                FoodDetailActivity.this.finish();
            }
        });
    }
}
