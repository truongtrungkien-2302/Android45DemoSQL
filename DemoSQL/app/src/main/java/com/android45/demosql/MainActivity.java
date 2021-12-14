package com.android45.demosql;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android45.demosql.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLHelper sqlHelper;
    ActivityMainBinding activityMainBinding;
    RecyclerView rvListFoods;
    FoodsAdapter foodsAdapter;
    ArrayList<String> foodsID, foodsName, foodsQuantity, foodsPrice;

    List<Foods> foodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        sqlHelper = new SQLHelper(MainActivity.this);

        activityMainBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataFoods();
            }
        });

        foodsID = new ArrayList<>();
        foodsName = new ArrayList<>();
        foodsQuantity = new ArrayList<>();
        foodsPrice = new ArrayList<>();

        storeDataInArrays();
        rvListFoods = findViewById(R.id.rvListFoods);
        rvListFoods.setLayoutManager(new GridLayoutManager(getBaseContext(), 1, RecyclerView.VERTICAL, false));
        foodsAdapter = new FoodsAdapter(MainActivity.this, this, foodsID, foodsName, foodsQuantity, foodsPrice);
        rvListFoods.setAdapter(foodsAdapter);

        activityMainBinding.btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllDataFoods();
            }
        });
    }

    private void deleteAllDataFoods() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete All?")
                .setMessage("Are you sure you want to delete all data?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqlHelper.onDeleteAll();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.show();
    }

    private void addDataFoods() {
        String name = activityMainBinding.etName.getText().toString().trim();
        String quantity = activityMainBinding.etQuantity.getText().toString().trim();
        String price = activityMainBinding.etPrice.getText().toString().trim();

        Foods foods = new Foods(1, name, quantity, price);

        sqlHelper.addFoods(foods);

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void storeDataInArrays() {
        Cursor cursor = sqlHelper.readAllData();
        while (cursor.moveToNext()) {
            foodsID.add(cursor.getString(0));
            foodsName.add(cursor.getString(1));
            foodsQuantity.add(cursor.getString(2));
            foodsPrice.add(cursor.getString(3));
        }
    }
}