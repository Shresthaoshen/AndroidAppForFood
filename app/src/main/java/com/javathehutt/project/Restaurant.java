package com.javathehutt.project;

public class Restaurant {

    private String name;
    private String price;
    private String rating;
    private String notes;
    private String tags;

    public Restaurant (String name) {
        setName(name);
    }

    public Restaurant(String name, String price, String rating, String notes, String tags) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.notes = notes;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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
