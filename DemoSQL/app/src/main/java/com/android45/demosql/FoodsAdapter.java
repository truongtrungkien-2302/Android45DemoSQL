package com.android45.demosql;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.Viewholder> {
    Context context;
    Activity activity;
    ArrayList foodsID, foodsName, foodsQuantity, foodsPrice;

    public FoodsAdapter(Context context, Activity activity, ArrayList foodsID, ArrayList foodsName, ArrayList foodsQuantity, ArrayList foodsPrice) {
        this.context = context;
        this.activity = activity;
        this.foodsID = foodsID;
        this.foodsName = foodsName;
        this.foodsQuantity = foodsQuantity;
        this.foodsPrice = foodsPrice;
    }

    @NonNull
    @Override
    public FoodsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.icon_recyclerview, parent, false);

        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvIDRv.setText(String.valueOf(foodsID.get(position)));
        holder.tvNameRv.setText(String.valueOf(foodsName.get(position)));
        holder.tvQuantityRv.setText(String.valueOf(foodsQuantity.get(position)));
        holder.tvPriceRv.setText(String.valueOf(foodsPrice.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("ID", String.valueOf(foodsID.get(position)));
                intent.putExtra("Name", String.valueOf(foodsName.get(position)));
                intent.putExtra("Quantity", String.valueOf(foodsQuantity.get(position)));
                intent.putExtra("Price", String.valueOf(foodsPrice.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodsID.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvIDRv, tvNameRv, tvQuantityRv, tvPriceRv;
        LinearLayout mainLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvIDRv = itemView.findViewById(R.id.tvIDRv);
            tvNameRv = itemView.findViewById(R.id.tvNameRv);
            tvQuantityRv = itemView.findViewById(R.id.tvQuantityRv);
            tvPriceRv = itemView.findViewById(R.id.tvPriceRv);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
