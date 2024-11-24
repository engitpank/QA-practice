package lesson7Files.dto;

import com.google.gson.annotations.SerializedName;

public class Glossary {
    public String title;
    @SerializedName("GlossDiv")
    public GlossDiv glossDiv;

    public class GlossDiv {
        public String title;
    }
}