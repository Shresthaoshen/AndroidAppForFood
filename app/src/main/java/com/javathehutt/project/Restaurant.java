package com.javathehutt.project;

public class Restaurant {

    private int ID;
    private String name;
    private Double price;
    private Double rating;
    private String notes;
    private String tags;

    public Restaurant (int ID, String name) {

        setID(ID);
        setName(name);
    }

    public Restaurant(int ID, String name, Double price, Double rating, String notes, String tags) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.notes = notes;
        this.tags = tags;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
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
