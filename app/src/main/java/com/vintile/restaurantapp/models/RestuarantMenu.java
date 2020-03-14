
package com.vintile.restaurantapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "menuitems")
public class RestuarantMenu {

    @PrimaryKey
    @NonNull
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("item_count")
    @Expose
    private String itemCount;
    @SerializedName("selected")
    @Expose
    private String selected;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("ingredients")
    @Expose
    private String ingredients;

    @Ignore
    public RestuarantMenu() {
    }

    public RestuarantMenu(String itemId, String title, String category, String itemCount, String selected, String price, String ingredients) {
        this.itemId = itemId;
        this.title = title;
        this.category = category;
        this.itemCount = itemCount;
        this.selected = selected;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
