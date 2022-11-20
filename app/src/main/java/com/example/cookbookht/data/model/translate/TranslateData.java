package com.example.cookbookht.data.model.translate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateData {

@SerializedName("translations")
@Expose
public List<Translation> translations = null;

}