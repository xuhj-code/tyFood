package com.food.ty.tyfood.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by ty on 2017/3/9.
 */

public class Food extends Model implements Serializable{
    public String foodid;
    public String shopid;
    public String foodName;
    public String foodImage;
    public String foodPrice;
    public int mCount=0;


}
