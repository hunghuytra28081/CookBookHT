package com.example.cookbookht.data.model.translate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslateResponse {

@SerializedName("data")
@Expose
public TranslateData data;
}