package com.vintile.restaurantapp.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vintile.restaurantapp.models.RestuarantMenu;

import java.util.List;

/**
 * Created by Sharath on 2020-03-13.
 */

@Dao
public interface MenuDao {

    @Insert
    void insertAll(List<RestuarantMenu> restuarantMenuList);

    @Query("SELECT * FROM menuitems")
    LiveData<List<RestuarantMenu>> getAll();

    @Query("SELECT * FROM menuitems WHERE selected = '1'")
    LiveData<List<RestuarantMenu>> getCartMenu();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCart(RestuarantMenu restuarantMenu);
}
