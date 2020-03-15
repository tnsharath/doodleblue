package com.vintile.restaurantapp.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vintile.restaurantapp.data.Repository;
import com.vintile.restaurantapp.models.RestuarantMenu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Sharath on 2020/03/13
 **/
public class CartViewModel extends ViewModel {

    private final LiveData<List<RestuarantMenu>> searchResults;
    private final MutableLiveData<Double> count = new MutableLiveData<>();
    private final MutableLiveData<Map<String, RestuarantMenu>> cartList = new MutableLiveData<>();
    private final Map<String, RestuarantMenu> cart;

    @Inject
    public CartViewModel(Repository repository) {
        searchResults = repository.getCartList();
        cart = new HashMap<>();
    }

    public LiveData<List<RestuarantMenu>> getMenu() {
        return searchResults;
    }

    private void setTotalPrice(double price) {
        count.setValue(price);
    }

    public LiveData<Double> getTotalPrice() {
        return count;
    }

    private void updateTotalPrice() {

        Iterator it = cart.entrySet().iterator();
        double price = 0.0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            RestuarantMenu menu = (RestuarantMenu) pair.getValue();
            price += Integer.parseInt(menu.getPrice()) * Integer.parseInt(menu.getItemCount());
        }
        setTotalPrice(price);
    }

    public void setCartList(RestuarantMenu menu) {
        cart.put(menu.getItemId(), menu);
        cartList.setValue(cart);
        updateTotalPrice();
    }

}
