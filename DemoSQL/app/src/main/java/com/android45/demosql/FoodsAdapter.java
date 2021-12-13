package com.android45.demosql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.Viewholder> {
    List<Foods> foodsList;

    public FoodsAdapter(List<Foods> foodsList) {
        this.foodsList = foodsList;
    }

    @NonNull
    @Override
    public FoodsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.icon_recyclerview, parent, false);

        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Foods foods = foodsList.get(position);

        holder.tvIDRv.setText(foods.getId());
        holder.tvNameRv.setText(foods.getName());
        holder.tvQuantityRv.setText(foods.getQuantity());
        holder.tvPriceRv.setText(foods.getPrice());
    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvIDRv, tvNameRv, tvQuantityRv, tvPriceRv;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvIDRv = itemView.findViewById(R.id.tvIDRv);
            tvNameRv = itemView.findViewById(R.id.tvNameRv);
            tvQuantityRv = itemView.findViewById(R.id.tvQuantityRv);
            tvPriceRv = itemView.findViewById(R.id.tvPriceRv);
        }
    }
}
