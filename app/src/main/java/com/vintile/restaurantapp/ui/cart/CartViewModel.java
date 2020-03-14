package com.vintile.restaurantapp.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vintile.restaurantapp.data.Repository;
import com.vintile.restaurantapp.models.RestuarantMenu;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Sharath on 2020/03/13
 **/
public class CartViewModel extends ViewModel {

    private LiveData<List<RestuarantMenu>> searchResults;

    @Inject
    public CartViewModel(Repository repository) {
        searchResults = repository.getCartList();

    }

    public LiveData<List<RestuarantMenu>> getMenu() {
        return searchResults;
    }
}
