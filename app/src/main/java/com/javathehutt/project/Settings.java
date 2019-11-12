package com.javathehutt.project;

public class Settings {
    private String key;
    private Boolean value;

    public Settings() {
    }

    public Settings(String key, Boolean value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String toString(){
        return "key: " + key + " value: " + value;
    }
}
