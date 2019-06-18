package com.example.foodiemp5app;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MealItemAdapter extends
            RecyclerView.Adapter<MealItemAdapter.MealViewHolder>
{

        private Context context;
        private ArrayList<MealItem> meals;
        class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private  TextView mealTitleView;
            private TextView mealDescription;
            private ImageView foodImage;

            final MealItemAdapter mAdapter;

            public MealViewHolder(View itemView, MealItemAdapter adapter)
            {
                super(itemView);
                mealTitleView = itemView.findViewById(R.id.food_title);
                mealDescription = itemView.findViewById(R.id.food_desc);
                foodImage = itemView.findViewById(R.id.food_photo);
                this.mAdapter = adapter;
                itemView.setOnClickListener(this);

                itemView.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View view)
                    {
                        final int p=getLayoutPosition();

                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                        alertDialog.setTitle("Alert");

                        alertDialog.setMessage(R.string.delete_meal_dialog);

                        alertDialog.setPositiveButton("No", new
                                DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        // User clicked OK button.
                                        // ... Action to take when OK is clicked.
                                    }
                                });
                        alertDialog.setNegativeButton("Delete", new
                                DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        meals.remove(p);
                                        notifyDataSetChanged();
                                    }
                                });
                        alertDialog.show();
                        return true;// returning true instead of false, works for me
                    }
                });
            }

            @Override
            public void onClick(View view)
            {
                int mPosition = getLayoutPosition();

                String element = meals.get(mPosition).title;
                Context context = view.getContext();

                Pair[] pairs = new Pair[3];

                pairs[0]= new Pair<View, String>(foodImage, "imageTransition");
                pairs[1]= new Pair<View, String>(mealTitleView, "titleTransition");
                pairs[2]= new Pair<View, String>(mealDescription, "descTransition");

                Intent intent = new Intent(context , MealItemActivity.class);
                intent.putExtra("title", mealTitleView.getText());
                intent.putExtra("description", mealDescription.getText());
                intent.putExtra("imageId", meals.get(mPosition).getImageId());

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);

                context.startActivity(intent, options.toBundle());

                mAdapter.notifyDataSetChanged();
            }
            public void setDetails(MealItem meal)
            {
                mealTitleView.setText(meal.getTitle());
                mealDescription.setText(meal.getDescription());
                foodImage.setImageResource(meal.getImageId());
            }

        }

        public MealItemAdapter(Context context, ArrayList<MealItem> meals)
        {
            this.context = context;
            this.meals = meals;
        }

        @Override
        public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.meal_item, parent, false);
            return new MealViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(MealViewHolder holder, int position)
        {
            MealItem meal = meals.get(position);
            holder.setDetails(meal);
        }

        @Override
        public int getItemCount()
        {
            return meals.size();
        }
    }