package com.r.indian_rivers;

import android.util.Log;

import java.util.Comparator;

public class ContactInfo {
    protected String length;
    protected String source;
    protected String destination;
    protected String name;
    protected String about;
    protected String left_tributaries;
    protected String right_tributaries;
    protected String dam;
    protected String mythology;
    protected String summary;
    protected String indian_length;
    protected String major;


    protected static final String ELength  = "Length : ";
    protected static final String HLength  = "लंबाई : ";
    protected static final String ESource = "Source : ";
    protected static final String HSource = "स्रोत : ";
    protected static final String EDestination = "Destination : ";
    protected static final String HDestination = "गंतव्य : ";
    protected static final String EindianLength = "Length in India : ";
    protected static final String HindianLength = "भारत में लंबाई : ";

    public static Comparator<ContactInfo> RiverName = new Comparator<ContactInfo>() {

        @Override
        public int compare(ContactInfo o1, ContactInfo o2) {
            String name=o1.name.trim();
            String name1=o2.name.trim();

            return name.compareToIgnoreCase(name1);
        }};

    /*Comparator for sorting the list by roll no*/
    public static Comparator<ContactInfo> RiverLegth = new Comparator<ContactInfo>() {

        @Override
        public int compare(ContactInfo o1, ContactInfo o2) {
            int l1=0;
            int l2=0;
            if(MainActivity.lang=="Hindi")
            {
                String d1[]=o1.length.split("k");
                String d2[]=o2.length.split("k");
                l1= Integer.parseInt(d1[0].replaceAll("[\\D]", "").trim());
                l2 =Integer.parseInt( d2[0].replaceAll("[\\D]", "").trim());
            }
            else
            {
                String d1[]=o1.length.split("कि");
                String d2[]=o2.length.split("कि");
                l1= Integer.parseInt(d1[0].replaceAll("[\\D]", "").trim());
                l2 =Integer.parseInt( d2[0].replaceAll("[\\D]", "").trim());
            }



            /*For ascending order*/
            return l2-l1;


        }};




}

