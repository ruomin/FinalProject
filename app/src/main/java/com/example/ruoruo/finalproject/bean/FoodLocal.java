package com.example.ruoruo.finalproject.bean;


import java.io.Serializable;

public class FoodLocal implements Serializable {
    private String foodId;

    private String uri;

    private String label;

    private String nutrients;

    private String category;

    private String categoryLabel;

    private String tag;

    public FoodLocal(String foodId, String uri, String label, String nutrients, String category, String categoryLabel, String tag) {
        this.foodId = foodId;
        this.uri = uri;
        this.label = label;
        this.nutrients = nutrients;
        this.category = category;
        this.categoryLabel = categoryLabel;
        this.tag = tag;
    }

    public FoodLocal() {
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNutrients() {
        return nutrients;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
