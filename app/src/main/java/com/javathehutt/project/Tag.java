package com.javathehutt.project;

public class Tag {

    private int ID;
    private String tagName;

    public Tag() {

    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(int ID, String tagName) {
        this.ID = ID;
        this.tagName = tagName;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTagName(String tag_name) {
        this.tagName = tagName;
    }

    // getter
    public int getId() {
        return this.ID;
    }

    public String getTagName() {
        return this.tagName;
    }
}
