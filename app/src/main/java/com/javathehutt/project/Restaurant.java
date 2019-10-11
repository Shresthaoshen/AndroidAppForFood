package com.javathehutt.project;

public class Restaurant {

    private String name;
    private double price;
    private double rating;
    private String notes;
    private String tags;

    public Restaurant (String name) {
        setName(name);
    }

    public Restaurant(String name, double price, double rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Restaurant Name: " + name + "\n" +
            "Price: $" +  price + "\n" +
            "Rating: " + rating + "\n" +
            "Notes:\n" + notes + "\n"+
            "Tags:" + tags;
    }

}
