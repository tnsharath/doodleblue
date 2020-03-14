package com.vintile.restaurantapp.di.cart;

import androidx.lifecycle.ViewModel;

import com.vintile.restaurantapp.di.ViewModelKey;
import com.vintile.restaurantapp.ui.cart.CartViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Sharath on 2020/03/13
 **/
@Module
public abstract class CartViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel.class)
    public abstract ViewModel bindCartViewModel(CartViewModel viewModel);
}
