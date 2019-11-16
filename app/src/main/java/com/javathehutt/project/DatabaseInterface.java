package com.javathehutt.project;

import java.util.ArrayList;
import java.util.List;

// -------------- STRATEGY DESIGN PATTERN -------------- //

public interface DatabaseInterface {

    // -------------- RESTAURANTS -------------- //
    boolean createRestaurant(String restaurantName, Double price, Double rating, String notes, ArrayList<String> tag_names);

    Restaurant getRestaurant(long restaurant_id);

    List<Restaurant> getAllRestaurants(String dataSortType, String dataSortOrder, String dataSearchQuery);

    List<Restaurant> getAllRestaurantsByTag(String tag_name);

    long createRestaurantTag(long todo_id, long tag_id);

    boolean updateData(String id, String restaurantName, Double price, Double rating, String notes);

    boolean deleteData(String id);

    // -------------- TAGS -------------- //

    long createTag(String tagName);

    ArrayList<Tag> getRestaurantsTags(int restaurant_id);

    void deleteTag(int tagID);

    // -------------- SETTINGS -------------- //

    double[] getPriceList();

    void createSettings(ArrayList<Settings> settingsData);

    int updateSettings(ArrayList<Settings> settingsData);

    ArrayList<Settings> getSettings();

    boolean settingsExist();

}
