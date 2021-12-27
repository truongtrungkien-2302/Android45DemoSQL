package com.android45.demosql;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android45.demosql.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding activityUpdateBinding;
    //    EditText etUpdateID, etUpdateName, etUpdateQuantity, etUpdatePrice;
    String foodsID, foodsName, foodsQuantity, foodsPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        activityUpdateBinding = DataBindingUtil.setContentView(UpdateActivity.this, R.layout.activity_update);

        getAndSetIntentData();

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null)
//            actionBar.setTitle(foodsName);

        activityUpdateBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLHelper sqlHelper = new SQLHelper(UpdateActivity.this);

                foodsName = activityUpdateBinding.etUpdateName.getText().toString().trim();
                foodsQuantity = activityUpdateBinding.etUpdateQuantity.getText().toString().trim();
                foodsPrice = activityUpdateBinding.etUpdatePrice.getText().toString().trim();

                sqlHelper.onUpdateFoods(foodsID, foodsName, foodsQuantity, foodsPrice);

                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        activityUpdateBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }

    private void confirmDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(UpdateActivity.this)
                .setTitle("Delete " + foodsName + "?")
                .setMessage("Are you sure you want to delete " + foodsName + "?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLHelper sqlHelper = new SQLHelper(UpdateActivity.this);
                        sqlHelper.onDeleteFoods(foodsID);
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
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

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("ID") && getIntent().hasExtra("Name")
                && getIntent().hasExtra("Quantity") && getIntent().hasExtra("Price")) {
            foodsID = getIntent().getStringExtra("ID");
            foodsName = getIntent().getStringExtra("Name");
            foodsQuantity = getIntent().getStringExtra("Quantity");
            foodsPrice = getIntent().getStringExtra("Price");

            activityUpdateBinding.etUpdateName.setText(foodsName);
            activityUpdateBinding.etUpdateQuantity.setText(foodsQuantity);
            activityUpdateBinding.etUpdatePrice.setText(foodsPrice);

            Log.d("stev", foodsName + " " + foodsQuantity + " " + foodsPrice);
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}