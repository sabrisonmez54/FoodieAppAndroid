package com.example.foodiemp5app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MealItemAdapter mAdapter;
    private ArrayList<MealItem> mealsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final EditText newName = findViewById(R.id.add_meal_text);
                final EditText newDesc = findViewById(R.id.add_description_text);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view1 = inflater.inflate(R.layout.layout_add_dialog, null);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setView(view1)

                .setTitle("Add new item")
                //alertDialog.setMessage(R.string.add_meal_dialog);

                .setPositiveButton("Add", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MealItem meal = new MealItem(newName.getText().toString(), newDesc.getText().toString(), R.drawable.donut_circle);
                                mealsArrayList.add(meal);
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

        mRecyclerView = findViewById(R.id.recyclerview);
        mealsArrayList = new ArrayList<>();
        mAdapter = new MealItemAdapter(this, mealsArrayList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        initializeData();

    }

    private void initializeData(){
        MealItem meal = new MealItem("Donut", "delicious glazed donut", R.drawable.donut_circle);
        mealsArrayList.add(meal);
        meal = new MealItem("Froyo", "savory frozen yogurt", R.drawable.froyo_circle);
        mealsArrayList.add(meal);
        meal = new MealItem("Ice Cream Sandwich", "tasty ice cream sandwich", R.drawable.icecream_circle);
        mealsArrayList.add(meal);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
