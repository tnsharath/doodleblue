package com.vintile.restaurantapp.ui.restuarantmenu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vintile.restaurantapp.data.Repository;
import com.vintile.restaurantapp.models.RestuarantMenu;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Sharath on 2020/03/13
 **/
public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";

    private  Repository repository;
    private LiveData<List<RestuarantMenu>> searchResults;

    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
        searchResults = repository.getMenuList();
    }

    public void getDataFromAPI() {
        repository.scheduleJobGetBonusProducts();
    }

    public LiveData<List<RestuarantMenu>> getMenu() {
        return searchResults;
    }

    public void updateCartTable(Map<String, RestuarantMenu> cartChoice) {

        Iterator it = cartChoice.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            RestuarantMenu menu = (RestuarantMenu) pair.getValue();
            Log.d(TAG, "updateCartTable: " + menu.getSelected());
            repository.updateCart(menu);
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
