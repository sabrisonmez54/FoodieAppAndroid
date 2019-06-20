package com.example.foodiemp5app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import static android.graphics.BitmapFactory.decodeFile;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private MealItemAdapter mAdapter;
    private ArrayList<MealItem> mealsArrayList;
    private ImageView imageChosen;
    private final int SELECT_PHOTO = 1;
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".I_AM_HOME";
    private HomeBroadCastReceiver mReceiver = new HomeBroadCastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerview);
        mealsArrayList = new ArrayList<>();
        mAdapter = new MealItemAdapter(this, mealsArrayList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);
        initializeData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View view1 = inflater.inflate(R.layout.layout_add_dialog, null);

                final EditText newName = view1.findViewById(R.id.add_meal_text);
                final EditText newDesc = view1.findViewById(R.id.add_description_text);
                final Button chooseBtn = view1.findViewById(R.id.choose_image_button);

                chooseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(pickPhoto , 1);
                    }
                });

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setView(view1)

                        .setTitle("Add new item")
                        //alertDialog.setMessage(R.string.add_meal_dialog);

                        .setPositiveButton("Add", new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        MealItem newMeal = new MealItem(newName.getText().toString(), newDesc.getText().toString(), R.drawable.donut_circle);
                                        mealsArrayList.add(newMeal);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Cancel", new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) { }
                                });
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){


                    Uri imageUri = imageReturnedIntent.getData();
                    File file = new File(imageUri.toString());

                    Picasso.get().load(file).centerCrop().resize(200,200).into(imageChosen);

                    Log.d("URI: ", imageUri.toString());
                }
        }
    }

    private void initializeData()
    {
        MealItem meal = new MealItem("Donut", "delicious glazed donut", R.drawable.donut_circle);
        mealsArrayList.add(meal);
        meal = new MealItem("Froyo", "savory frozen yogurt", R.drawable.froyo_circle);
        mealsArrayList.add(meal);
        meal = new MealItem("Ice Cream Sandwich", "tasty ice cream sandwich", R.drawable.icecream_circle);
        mealsArrayList.add(meal);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        if (id == R.id.action_home)
        {
            LocalBroadcastManager.getInstance(this)
                    .registerReceiver(mReceiver,
                            new IntentFilter(ACTION_CUSTOM_BROADCAST));

            Intent intent = new Intent(this , MealItemActivity.class);

            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(mealsArrayList.size());
            MealItem mealItem = mealsArrayList.get(index);

            intent.putExtra("title", mealItem.title);
            intent.putExtra("description", mealItem.description);
            intent.putExtra("imageId", mealItem.imageId);

            startActivity(intent);

            Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);

            LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        //Unregister the receiver
        this.unregisterReceiver(mReceiver);
        super.onDestroy();

        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);
    }


}
