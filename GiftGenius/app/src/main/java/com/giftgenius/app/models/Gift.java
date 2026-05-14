package com.giftgenius.app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "gifts")
public class Gift {
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private int price;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("category")
    private String category;

    @SerializedName("tags")
    private String tags;

    @SerializedName("ozon_link")
    private String ozonLink;

    @SerializedName("wb_link")
    private String wbLink;

    private boolean isFavorite;

    public Gift(int id, String name, String description, int price,
                String imageUrl, String category, String tags,
                String ozonLink, String wbLink) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.tags = tags;
        this.ozonLink = ozonLink;
        this.wbLink = wbLink;
        this.isFavorite = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getOzonLink() { return ozonLink; }
    public void setOzonLink(String ozonLink) { this.ozonLink = ozonLink; }

    public String getWbLink() { return wbLink; }
    public void setWbLink(String wbLink) { this.wbLink = wbLink; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
