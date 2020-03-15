package com.vintile.restaurantapp.ui.restuarantmenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vintile.restaurantapp.data.Repository;
import com.vintile.restaurantapp.models.RestuarantMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Sharath on 2020/03/13
 **/
public class MainViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<List<RestuarantMenu>> searchResults;
    private final Map<String, RestuarantMenu> cart;
    private final MutableLiveData<Integer> cartCount;

    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
        searchResults = repository.getMenuList();
        cartCount = new MutableLiveData<>();
        cart = new HashMap<>();
    }

    public void getDataFromAPI() {
        repository.scheduleJobGetBonusProducts();
    }

    public LiveData<List<RestuarantMenu>> getMenu() {
        return searchResults;
    }

    public void updateCartTable() {
        for (Map.Entry<String, RestuarantMenu> stringRestuarantMenuEntry : cart.entrySet()) {
            RestuarantMenu menu = (RestuarantMenu) ((Map.Entry) stringRestuarantMenuEntry).getValue();
            repository.updateCart(menu);
        }
    }
    public void setCartList(RestuarantMenu menu) {
        cart.put(menu.getItemId(), menu);
        setCartListCount();
    }

    private void setCartListCount() {
        cartCount.setValue(cartItemCount());
    }

    private int cartItemCount(){
        int count = 0;
        for (Map.Entry<String, RestuarantMenu> stringRestuarantMenuEntry : cart.entrySet()) {
            RestuarantMenu menu = (RestuarantMenu) ((Map.Entry) stringRestuarantMenuEntry).getValue();
            count += Integer.parseInt(menu.getItemCount());
        }
        return count;
    }

    public LiveData<Integer> getCartItemCount(){
        return cartCount;
    }
}
