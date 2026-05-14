package com.giftgenius.app.models;

import com.google.gson.annotations.SerializedName;

public class GiftRequest {
    @SerializedName("recipient")
    private String recipient;

    @SerializedName("occasion")
    private String occasion;

    @SerializedName("interests")
    private String interests;

    @SerializedName("budget")
    private String budget;

    public GiftRequest(String recipient, String occasion, String interests, String budget) {
        this.recipient = recipient;
        this.occasion = occasion;
        this.interests = interests;
        this.budget = budget;
    }
}
