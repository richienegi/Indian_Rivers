package com.r.indian_rivers;

public class rivers {
    int id;
    String name;
    String length;
    String source;
    String destination;
    String codeno;
    String about;
    String left_tributaries;
    String right_tributaries;
    String dam;
    String mythology;
    String summary;

    public rivers(int id, String name, String length, String source, String destination, String codeno, String about, String left_tributaries, String right_tributaries, String dam, String mythology, String summary) {
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
}
