package com.android45.demosql;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android45.demosql.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    SQLHelper sqlHelper;
    ActivityMainBinding activityMainBinding;
    FoodsAdapter foodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        sqlHelper = new SQLHelper(this);

        activityMainBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = activityMainBinding.etName.getText().toString();
                String quantity = activityMainBinding.etQuantity.getText().toString();
                String price = activityMainBinding.etPrice.getText().toString();

                Foods foods = new Foods(1, name, quantity, price);

                sqlHelper.addFoods(foods);
            }
        });

        activityMainBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = activityMainBinding.etName.getText().toString();
                String quantity = activityMainBinding.etQuantity.getText().toString();
                String price = activityMainBinding.etPrice.getText().toString();

                Foods foods = new Foods(1, name, quantity, price);

                sqlHelper.onUpdateFoods("1", foods);
            }
        });

        activityMainBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = activityMainBinding.etName.getText().toString();
                String quantity = activityMainBinding.etQuantity.getText().toString();
                String price = activityMainBinding.etPrice.getText().toString();

                Foods foods = new Foods(1, name, quantity, price);

                sqlHelper.onDeleteFoods("1");
//                sqlHelper.onDeleteAll();
            }
        });
    }
}