package com.vintile.restaurantapp.ui.restuarantmenu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vintile.restaurantapp.R;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.util.MainAdapterInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sharath on 2020/03/12
 **/
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RestuarantMenu> restuarantMenus = new ArrayList<>();

    MainAdapterInterface mainAdapterInterface;

    public MenuAdapter(MainAdapterInterface mainAdapterInterface) {
        this.mainAdapterInterface = mainAdapterInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder) holder).bind(restuarantMenus.get(position));
    }

    @Override
    public int getItemCount() {
        return restuarantMenus.size();
    }

    public void setMenus(List<RestuarantMenu> restuarantMenus) {
        this.restuarantMenus = restuarantMenus;
        notifyDataSetChanged();
    }

    public void refreshList(List<RestuarantMenu> restuarantMenus) {
        this.restuarantMenus.clear();
        this.restuarantMenus = restuarantMenus;
        notifyDataSetChanged();
    }

    private static final String TAG = "MenuAdapter";
    Map<String, RestuarantMenu> cartChoice = new HashMap<>();
    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView tvIngredients, tvCost;
        private TextView btnMinus, tvCount, btnPlus;
        private Button btnAdd;

        private RelativeLayout countPicker;
        private int countInInt = 0;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            tvIngredients = itemView.findViewById(R.id.tvIngredients);
            tvCost = itemView.findViewById(R.id.tvCost);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            tvCount = itemView.findViewById(R.id.tvCount);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            countPicker = itemView.findViewById(R.id.countPicker);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }

        public void bind(RestuarantMenu restuarantMenu) {
            RestuarantMenu restuarantMenuSelected = restuarantMenu;
            Log.d(TAG, "bind: " + restuarantMenu.getIngredients() + "  " + restuarantMenu.getPrice() + "  " + restuarantMenu.getItemCount());
            title.setText(restuarantMenu.getTitle());
            tvIngredients.setText(restuarantMenu.getIngredients());
            tvCost.setText(restuarantMenu.getPrice());

            String count = restuarantMenu.getItemCount();
            tvCount.setText(count);
            countInInt = Integer.parseInt(count);
            if (countInInt > 0) {
                changeCounter(false);
            } else {
                changeCounter(true);
            }

            btnMinus.setOnClickListener(v -> {
                if (countInInt > 1) {
                    countInInt -= 1;
                    tvCount.setText(String.valueOf(countInInt));
                    updateCount(false);
                    restuarantMenuSelected.setItemCount(String.valueOf(countInInt));
                    cartChoice.put(restuarantMenuSelected.getItemId(), restuarantMenuSelected);
                } else {
                    countInInt -= 1;
                    tvCount.setText(String.valueOf(countInInt));
                    changeCounter(true);
                    updateCount(false);
                    cartChoice.remove(restuarantMenuSelected.getItemId());
                }
            });
            btnPlus.setOnClickListener(v -> {
                if (countInInt < 20) {
                    countInInt += 1;
                    tvCount.setText(String.valueOf(countInInt));
                    updateCount(true);
                    restuarantMenuSelected.setSelected("1");
                    restuarantMenuSelected.setItemCount(String.valueOf(countInInt));
                    cartChoice.put(restuarantMenuSelected.getItemId(), restuarantMenuSelected);
                } else {
                    //TODO toast to show
                }
            });

            btnAdd.setOnClickListener(v -> {
                countInInt += 1;
                tvCount.setText(String.valueOf(countInInt));
                changeCounter(false);
                restuarantMenuSelected.setSelected("1");
                restuarantMenuSelected.setItemCount("1");
                cartChoice.put(restuarantMenuSelected.getItemId(), restuarantMenuSelected);
                updateCount(true);
            });

        }

        private void changeCounter(boolean disableCounter) {
            if (disableCounter) {
                countPicker.setVisibility(View.INVISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
            } else {
                countPicker.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.INVISIBLE);
            }
        }
    }

    int count = 0;

    public Map<String, RestuarantMenu> getCartItem() {
        return cartChoice;
    }

    void updateCount(boolean isInc) {
        if (isInc)
            count += 1;
        else
            count -= 1;

        mainAdapterInterface.updateCount(count);
    }
}
