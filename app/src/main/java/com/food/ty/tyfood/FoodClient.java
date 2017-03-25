package com.food.ty.tyfood;

/**
 * Created by ty on 2017/3/8.
 */

public class FoodClient {
    private static FoodClient singleton;

    public static FoodClient getInstance() {
        if (singleton == null) {
            singleton = new FoodClient();
        }
        return singleton;
    }
    public String API="http://192.168.1.104:8080/tyfoodmenu";

}
