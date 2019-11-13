package com.javathehutt.project;

import java.util.List;

public interface DatabaseInterface {

    boolean createRestaurant(String restaurantName, Double price, Double rating, String notes, long[] tag_ids);

  //  Restaurant getRestaurant(long restaurant_id);

    List<Restaurant> getAllRestaurantsByTag(String tag_name);

    boolean updateData(String id, String restaurantName, Double price, Double rating, String notes, String tags);

    boolean deleteData(String id);

    long createRestaurantTag(long todo_id, long tag_id);
}
