package com.example.lab_05_ver2.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_05_ver2.R;
import com.example.lab_05_ver2.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> implements Filterable {

    private ArrayList<DogBreed> dogs;
    private ArrayList<DogBreed> dogsOld;

    public DogAdapter(ArrayList<DogBreed> dogs, ArrayList<DogBreed> dogsOld){
        this.dogs = dogs;
        this.dogsOld = dogsOld;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DogBreed dogBreed = dogs.get(position);
        if (dogBreed == null){
            return;
        }
        // change color of heart
        Picasso.get().load(dogBreed.getUrl()).into(holder.ivAvatar);
        holder.tvNameDog.setText(dogBreed.getName());
        holder.tvBredFor.setText(dogBreed.getBredFor());
        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ivFavourite.setImageResource(R.drawable.icon_favourite_red);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dogs != null){
            return dogs.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    dogs = dogsOld;
                }else {
                    ArrayList<DogBreed> list = new ArrayList<DogBreed>();
                    for(DogBreed dogBreed : dogsOld){
                        if(dogBreed.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(dogBreed);
                        }
                    }
                    dogs = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dogs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogs = (ArrayList<DogBreed>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvNameDog;
        public TextView tvBredFor;
        public ImageView ivAvatar;
        public ImageView ivFavourite;
        public CardView cvItem;

        public ViewHolder(View view) {
            super(view);
            tvNameDog = view.findViewById(R.id.tv_name_dog);
            tvBredFor = view.findViewById(R.id.tv_bred_for);
            ivAvatar = view.findViewById(R.id.iv_avatar);
            ivFavourite = view.findViewById(R.id.iv_favourite);
            cvItem = view.findViewById(R.id.cv_item);
        }
    }

    public void setDogs(ArrayList<DogBreed> dogs){
        this.dogs = dogs;
        notifyDataSetChanged();
    }


}
