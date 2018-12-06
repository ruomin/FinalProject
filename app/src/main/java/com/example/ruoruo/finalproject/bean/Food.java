package com.example.ruoruo.finalproject.bean;


public class Food {
    private String foodId;

    private String uri;

    private String label;

    private Nutrients nutrients;

    private String category;

    private String categoryLabel;

    public Food(String foodId, String uri, String label, Nutrients nutrients, String category, String categoryLabel) {
        this.foodId = foodId;
        this.uri = uri;
        this.label = label;
        this.nutrients = nutrients;
        this.category = category;
        this.categoryLabel = categoryLabel;
    }

    public Food() {
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

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
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

    @Override
    public String toString() {
        return "Food{" +
                "foodId='" + foodId + '\'' +
                ", uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                ", nutrients=" + nutrients.toString() +
                ", category='" + category + '\'' +
                ", categoryLabel='" + categoryLabel + '\'' +
                '}';
    }
}
