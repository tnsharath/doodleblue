package com.vintile.restaurantapp.ui.restuarantmenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vintile.restaurantapp.R;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.util.MainAdapterInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharath on 2020/03/12
 **/
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RestuarantMenu> restuarantMenus = new ArrayList<>();

    private final MainAdapterInterface mainAdapterInterface;
    private int countInInt = 0;

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

    private class PostViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView tvIngredients;
        private final TextView tvCost;
        private final TextView btnMinus;
        private final TextView tvCount;
        private final TextView btnPlus;
        private final Button btnAdd;

        private final RelativeLayout countPicker;

        PostViewHolder(@NonNull View itemView) {
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

        void bind(RestuarantMenu restuarantMenu) {

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
                countInInt = Integer.parseInt(restuarantMenu.getItemCount()) - 1;
                tvCount.setText(String.valueOf(countInInt));
                if (countInInt < 1) {
                    changeCounter(true);
                    restuarantMenu.setSelected("0");
                }
                restuarantMenu.setItemCount(String.valueOf(countInInt));
                mainAdapterInterface.updatePrice(restuarantMenu);
            });

            btnPlus.setOnClickListener(v -> {
                if (countInInt < 20) {
                    countInInt = Integer.parseInt(restuarantMenu.getItemCount()) + 1;
                    tvCount.setText(String.valueOf(countInInt));
                    restuarantMenu.setSelected("1");
                    restuarantMenu.setItemCount(String.valueOf(countInInt));
                    mainAdapterInterface.updatePrice(restuarantMenu);
                } else {
                    Toast.makeText(tvCost.getContext(), "Max cart limit reached!!", Toast.LENGTH_SHORT).show();
                }
            });

            btnAdd.setOnClickListener(v -> {
                countInInt = 1;
                tvCount.setText(String.valueOf(countInInt));
                changeCounter(false);
                restuarantMenu.setSelected("1");
                restuarantMenu.setItemCount("1");
                mainAdapterInterface.updatePrice(restuarantMenu);
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
}
