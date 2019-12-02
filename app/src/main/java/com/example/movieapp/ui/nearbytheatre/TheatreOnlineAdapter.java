package com.example.movieapp.ui.nearbytheatre;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.ui.BookTicketActivity;

import java.util.ArrayList;

public class TheatreOnlineAdapter extends RecyclerView.Adapter<TheatreOnlineAdapter.ViewHolder> {
    private ArrayList<ResponseTheatre> listdata;
    Context context;


    public TheatreOnlineAdapter(Context context, ArrayList<ResponseTheatre> listdata) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResponseTheatre theatre = listdata.get(position);
        holder.tvPlaceName.setText(theatre.getPlaceName());
        holder.tvAddress.setText(theatre.getAddress());
        holder.tvRatting.setText("Rating: " + theatre.getRatting());
        holder.tvUserRatting.setText("Total User Ratings: " + theatre.getPeopleRatting());
        Log.e("getImageUrl:", "" + theatre.getImageUrl());
        Glide.with(context)
                .load(theatre.getImageUrl())
                .into(holder.ivLogo);
        holder.fmLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookTicketActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivLogo;
        public TextView tvPlaceName, tvAddress, tvRatting, tvUserRatting;
        FrameLayout fmLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            this.ivLogo = itemView.findViewById(R.id.ivLogo);
            this.tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            this.tvAddress = itemView.findViewById(R.id.tvAddress);
            this.tvRatting = itemView.findViewById(R.id.tvRatting);
            this.tvUserRatting = itemView.findViewById(R.id.tvUserRatting);
            fmLayout = itemView.findViewById(R.id.fmLayout);

        }
    }
}