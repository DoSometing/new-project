package com.develop.windexit.finalproject;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.develop.windexit.finalproject.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {


    TextView food_name_detail,food_price_detail,food_description_detail;
    ImageView food_imgae_detail;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";
    FirebaseDatabase database;
    DatabaseReference foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //init view

        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);
        food_description_detail = findViewById(R.id.food_description_detail);
        food_name_detail = findViewById(R.id.food_name_detail);
        food_imgae_detail = findViewById(R.id.img_food);
        food_price_detail = findViewById(R.id.food_price_detail);
        collapsingToolbarLayout = findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        //Get Food Id from Intent
        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty() && foodId != null) {
            getDetailFood(foodId);

        }
    }

    private void getDetailFood(final String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                //Set Image
                Picasso.with(getBaseContext()).load(food.getImage())
                        .into(food_imgae_detail);
                collapsingToolbarLayout.setTitle(food.getName());
                food_name_detail.setText(food.getName());
                food_price_detail.setText(food.getPrice());
                food_description_detail.setText(food.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
