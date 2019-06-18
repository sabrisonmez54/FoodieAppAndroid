package com.example.foodiemp5app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MealItemActivity extends AppCompatActivity
{
    private LinearLayout linearLayout;
    private TextView title;
    private TextView description;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_meal_item);

        linearLayout = findViewById(R.id.meal_activity_layout);
        title = findViewById(R.id.meal_activity_title);
        description = findViewById(R.id.meal_activity_desc);
        image = findViewById(R.id.meal_activity_image);

        Intent intent = getIntent();
        String mTitle = intent.getStringExtra("title");
        String mDesc = intent.getStringExtra("description");
        int mId = intent.getIntExtra("imageId", 0);
        title.setText(mTitle);
        description.setText(mDesc);
        image.setImageResource(mId);
    }
}

