package com.r.indian_rivers.model;

import com.google.gson.annotations.SerializedName;

public class Rivers {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("length")
    String length;

    @SerializedName("source")
    String source;

    @SerializedName("destination")
    String destination;

    @SerializedName("codeno")
    String codeno;

    @SerializedName("about")
    String about;

    @SerializedName("left_tributaries")
    String left_tributaries;

    @SerializedName("right_tributaries")
    String right_tributaries;

    @SerializedName("dam")
    String dam;

    @SerializedName("mythology")
    String mythology;

    @SerializedName("summary")
    String summary;

    @SerializedName("indian_length")
    String indian_length;

    @SerializedName("major")
    String major;

    public Rivers(int id, String name, String length, String source, String destination, String codeno, String about, String left_tributaries, String right_tributaries, String dam, String mythology, String summary, String indian_length, String major) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.source = source;
        this.destination = destination;
        this.codeno = codeno;
        this.about = about;
        this.left_tributaries = left_tributaries;
        this.right_tributaries = right_tributaries;
        this.dam = dam;
        this.mythology = mythology;
        this.summary = summary;
        this.indian_length = indian_length;
        this.major = major;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLength() {
        return length;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getCodeno() {
        return codeno;
    }

    public String getAbout() {
        return about;
    }

    public String getLeft_tributaries() {
        return left_tributaries;
    }

    public String getRight_tributaries() {
        return right_tributaries;
    }

    public String getDam() {
        return dam;
    }

    public String getMythology() {
        return mythology;
    }

    public String getSummary() {
        return summary;
    }

    public String getIndian_length() {
        return indian_length;
    }

    public String getMajor() {
        return major;
    }
}
