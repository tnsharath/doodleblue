package com.vintile.restaurantapp.di.cart;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.vintile.restaurantapp.network.MainApi;
import com.vintile.restaurantapp.ui.cart.CartActivity;

import com.vintile.restaurantapp.ui.restuarantmenu.MenuAdapter;
import com.vintile.restaurantapp.util.MainAdapterInterface;
import com.vintile.restaurantapp.util.VerticalSpacingItemDecoration;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Sharath on 2020/02/14
 **/
@Module
public class CartModule {

    @Provides
    static MainAdapterInterface provideInterface(CartActivity cartActivity) {
        return cartActivity;
    }

    @Provides
    static MenuAdapter provideAdapter(MainAdapterInterface mainAdapterInterface) {
        return new MenuAdapter(mainAdapterInterface);
    }

    @Provides
    static LinearLayoutManager provideLinearLayoutManager(CartActivity cartActivity) {
        return new LinearLayoutManager(cartActivity);
    }

    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static VerticalSpacingItemDecoration providesVerticalSpace(){
        return new VerticalSpacingItemDecoration(15);
    }


}
