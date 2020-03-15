package com.vintile.restaurantapp.ui.restuarantmenu;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.vintile.restaurantapp.R;
import com.vintile.restaurantapp.databinding.ActivityMainBinding;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.ui.cart.CartActivity;
import com.vintile.restaurantapp.util.MainAdapterInterface;
import com.vintile.restaurantapp.util.VerticalSpacingItemDecoration;
import com.vintile.restaurantapp.viewmodels.ViewModelProviderFactory;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


/**
 * Created by Sharath on 2020/03/12
 **/
public class MainActivity extends DaggerAppCompatActivity implements MainAdapterInterface {

    private MainViewModel viewModel;

    @Inject
    MenuAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    VerticalSpacingItemDecoration itemDecoration;

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        viewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);
        initRecyclerView();
        subscribeObserver();
        setItemCount();

        activityMainBinding.btnCheckout.setOnClickListener(v -> {
            updateCartTable();
            startActivity(new Intent(this, CartActivity.class));
        });
    }

    /**
     * set choosen Item count.
     */
    private void setItemCount() {
        viewModel.getCartItemCount().observe(this, count -> {
            if (count > 0) {
                activityMainBinding.btnCheckout.setEnabled(true);
                activityMainBinding.btnCheckout.setText(" VIEW CART (" + count + " ITEMS)");
            } else {
                activityMainBinding.btnCheckout.setEnabled(false);
                activityMainBinding.btnCheckout.setText(" VIEW CART (Empty)");
            }
        });
    }

    /**
     *  Subscribe observer to get menu items.
     */
    private void subscribeObserver() {
        viewModel.getDataFromAPI();
        viewModel.getMenu().observe(this, menus -> {
            adapter.setMenus(menus);
            for (RestuarantMenu menu : menus) {
                viewModel.setCartList(menu);
            }
        });
    }

    /**
     * Initialize recycler view.
     */
    private void initRecyclerView() {
        activityMainBinding.rvMenu.setLayoutManager(linearLayoutManager);
        activityMainBinding.rvMenu.addItemDecoration(itemDecoration);
        activityMainBinding.rvMenu.setAdapter(adapter);
    }

    /**
     * Update cartlist on every item added.
     */
    private void updateCartTable() {
        viewModel.updateCartTable();
    }

    @Override
    public void updatePrice(RestuarantMenu cartChoice) {
        viewModel.setCartList(cartChoice);
    }
}
