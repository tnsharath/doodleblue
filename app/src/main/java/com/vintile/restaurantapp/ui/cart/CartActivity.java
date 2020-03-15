package com.vintile.restaurantapp.ui.cart;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.vintile.restaurantapp.R;
import com.vintile.restaurantapp.databinding.ActivityCartBinding;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.ui.restuarantmenu.MenuAdapter;
import com.vintile.restaurantapp.util.MainAdapterInterface;
import com.vintile.restaurantapp.util.VerticalSpacingItemDecoration;
import com.vintile.restaurantapp.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class CartActivity extends DaggerAppCompatActivity implements MainAdapterInterface {

    private CartViewModel viewModel;

    @Inject
    MenuAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    VerticalSpacingItemDecoration itemDecoration;

    private ActivityCartBinding activityCartBinding;

    private List<RestuarantMenu> cartMenu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        viewModel = new ViewModelProvider(this, providerFactory).get(CartViewModel.class);
        initRecyclerView();
        subscribeObserver();
        updateTotalCost();
        activityCartBinding.rvCart.tvShowMore.setOnClickListener(v -> showMore());
    }

    /**
     * Update Total amount
     */
    private void updateTotalCost() {
        viewModel.getTotalPrice().observe(this, cost -> activityCartBinding.tvTotalCost.setText(String.valueOf(cost)));
    }


    /**
     * Click show more
     */
    private void showMore() {
        adapter.refreshList(cartMenu);
        activityCartBinding.rvCart.tvShowMore.setVisibility(View.INVISIBLE);
    }


    /**
     * Receive cart items and show only 2 items
     */
    private void subscribeObserver() {
        viewModel.getMenu().observe(this, menus -> {
            cartMenu = menus;

            for (RestuarantMenu menu : cartMenu) {
                viewModel.setCartList(menu);
            }
            if (menus.size() > 2) {
                List<RestuarantMenu> sortedMenu = new ArrayList<>();
                sortedMenu.add(menus.get(0));
                sortedMenu.add(menus.get(1));
                activityCartBinding.rvCart.tvShowMore.setVisibility(View.VISIBLE);
                adapter.setMenus(sortedMenu);
            } else {
                adapter.setMenus(cartMenu);
            }
        });
    }

    /**
     * Initialize recyclerview
     */
    private void initRecyclerView() {
        activityCartBinding.rvCart.rvCart.setLayoutManager(linearLayoutManager);
        activityCartBinding.rvCart.rvCart.addItemDecoration(itemDecoration);
        activityCartBinding.rvCart.rvCart.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.updateCartTable();
    }


    @Override
    public void updatePrice(RestuarantMenu cartChoice) {
        viewModel.setCartList(cartChoice);
    }
}
