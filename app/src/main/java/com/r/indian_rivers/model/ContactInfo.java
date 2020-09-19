package com.r.indian_rivers.model;

import com.r.indian_rivers.views.activity.MainActivity;

import java.util.Comparator;

public class ContactInfo {
    private String length;
    private String source;
    private String destination;
    private String name;
    private String about;
    private String left_tributaries;
    private String right_tributaries;
    private String dam;
    private String mythology;
    private String summary;
    private String indian_length;
    private String major;

    private final String ELength = "Length : ";
    private final String ESource = "Source : ";
    private final String EDestination = "Destination : ";
    private final String EIndianLength = "Length in India : ";

    private final String HLength = "लंबाई : ";
    private final String HSource = "स्रोत : ";
    private final String HDestination = "गंतव्य : ";
    private final String HIndianLength = "भारत में लंबाई : ";

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLeft_tributaries() {
        return left_tributaries;
    }

    public void setLeft_tributaries(String left_tributaries) {
        this.left_tributaries = left_tributaries;
    }

    public String getRight_tributaries() {
        return right_tributaries;
    }

    public void setRight_tributaries(String right_tributaries) {
        this.right_tributaries = right_tributaries;
    }

    public String getDam() {
        return dam;
    }

    public void setDam(String dam) {
        this.dam = dam;
    }

    public String getMythology() {
        return mythology;
    }

    public void setMythology(String mythology) {
        this.mythology = mythology;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIndian_length() {
        return indian_length;
    }

    public void setIndian_length(String indian_length) {
        this.indian_length = indian_length;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getELength() {
        return ELength;
    }

    public String getESource() {
        return ESource;
    }

    public String getEDestination() {
        return EDestination;
    }

    public String getEIndianLength() {
        return EIndianLength;
    }

    public String getHLength() {
        return HLength;
    }

    public String getHSource() {
        return HSource;
    }

    public String getHDestination() {
        return HDestination;
    }

    public String getHIndianLength() {
        return HIndianLength;
    }

    public static Comparator<ContactInfo> RiverName = new Comparator<ContactInfo>() {

        @Override
        public int compare(ContactInfo o1, ContactInfo o2) {
            String name = o1.name.trim();
            String name1 = o2.name.trim();
            return name.compareToIgnoreCase(name1);
        }
    };

    /*Comparator for sorting the list by roll no*/
    public static Comparator<ContactInfo> RiverLegth = new Comparator<ContactInfo>() {
        @Override
        public int compare(ContactInfo o1, ContactInfo o2) {
            int l1 = 0;
            int l2 = 0;
            if (MainActivity.lang.equals("Hindi")) {
                String d1[] = o1.length.split("k");
                String d2[] = o2.length.split("k");
                l1 = Integer.parseInt(d1[0].replaceAll("[\\D]", "").trim());
                l2 = Integer.parseInt(d2[0].replaceAll("[\\D]", "").trim());
            } else {
                String d1[] = o1.length.split("कि");
                String d2[] = o2.length.split("कि");
                l1 = Integer.parseInt(d1[0].replaceAll("[\\D]", "").trim());
                l2 = Integer.parseInt(d2[0].replaceAll("[\\D]", "").trim());
            }
            /*For ascending order*/
            return l2 - l1;
        }
    };

}

