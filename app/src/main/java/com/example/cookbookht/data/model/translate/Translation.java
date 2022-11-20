package com.example.cookbookht.data.model.translate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation {

    @SerializedName("translatedText")
    @Expose
    public String translatedText;

}