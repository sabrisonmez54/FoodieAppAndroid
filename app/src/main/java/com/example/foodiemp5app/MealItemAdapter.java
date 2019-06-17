package com.example.foodiemp5app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MealItemAdapter extends
            RecyclerView.Adapter<MealItemAdapter.MealViewHolder>  {

        private Context context;
        private ArrayList<MealItem> meals;
        public static final String Meal_MESSAGE =
            "com.example.android.mealActivity.extra.MESSAGE";

        class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private  TextView mealTitleView;
            private TextView mealDescription;
            private ImageView foodImage;

            final MealItemAdapter mAdapter;

            public MealViewHolder(View itemView, MealItemAdapter adapter) {
                super(itemView);
                mealTitleView = itemView.findViewById(R.id.food_title);
                mealDescription = itemView.findViewById(R.id.food_desc);
                foodImage = itemView.findViewById(R.id.food_photo);
                this.mAdapter = adapter;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int mPosition = getLayoutPosition();

                String element = meals.get(mPosition).title;

                Context context = view.getContext();
                Intent intent = new Intent(context , MealItemActivity.class);
                intent.putExtra(Meal_MESSAGE, element);
                context.startActivity(intent);
                mAdapter.notifyDataSetChanged();
            }

            public void setDetails(MealItem meal) {
                mealTitleView.setText(meal.getTitle());
                mealDescription.setText(meal.getDescription());
                foodImage.setImageResource(meal.getImageId());
            }
        }

        public MealItemAdapter(Context context, ArrayList<MealItem> meals) {
            this.context = context;
            this.meals = meals;
        }

        @Override
        public MealViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.meal_item, parent, false);
            return new MealViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(MealViewHolder holder,
                                     int position) {
            MealItem meal = meals.get(position);
            holder.setDetails(meal);
        }

        @Override
        public int getItemCount() {
            return meals.size();
        }
    }

