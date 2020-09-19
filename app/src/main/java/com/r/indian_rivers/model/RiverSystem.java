package com.r.indian_rivers.model;

import com.google.gson.annotations.SerializedName;

public class RiverSystem {

    @SerializedName("introduction")
    private String introduction;

    @SerializedName("classification")
    private String classification;

    @SerializedName("type1")
    private String type1;

    @SerializedName("type2")
    private String type2;

    @SerializedName("Himalayan")
    private String Himalayan;

    @SerializedName("Indus")
    private String Indus;

    @SerializedName("Ganga")
    private String Ganga;

    @SerializedName("Brahmaputra")
    private String Brahmaputra;

    @SerializedName("Peninsular")
    private String Peninsular;

    public RiverSystem(String introduction, String classification, String type1, String type2, String himalayan, String indus, String ganga, String brahmaputra, String peninsular) {
        this.introduction = introduction;
        this.classification = classification;
        this.type1 = type1;
        this.type2 = type2;
        Himalayan = himalayan;
        Indus = indus;
        Ganga = ganga;
        Brahmaputra = brahmaputra;
        Peninsular = peninsular;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getClassification() {
        return classification;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getHimalayan() {
        return Himalayan;
    }

    public String getIndus() {
        return Indus;
    }

    public String getGanga() {
        return Ganga;
    }

    public String getBrahmaputra() {
        return Brahmaputra;
    }

    public String getPeninsular() {
        return Peninsular;
    }
}