package com.javathehutt.project;

public class Settings {
    private String key;
    private int value;

    public Settings() {
    }

    public Settings(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString(){
        return "key: " + key + " value: " + value;
    }
}
