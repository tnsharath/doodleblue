package com.vintile.restaurantapp.util;

import com.vintile.restaurantapp.models.RestuarantMenu;

import java.util.Map;

/**
 * Created by Sharath on 2020/03/13
 **/
public interface MainAdapterInterface {
    void updateCount(int count);
    void updatePrice(Map<String, RestuarantMenu> cartChoice);
}
