package com.example.movieapp.ui.nearbytheatre;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on : Oct 10, 2019
 * Author     : Amit
 */
public class MovieAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "MovieAdapter";
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<TheatreList> movieList;

    public MovieAdapter(List<TheatreList> movieList) {
        this.movieList = movieList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (movieList != null && movieList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (movieList != null && movieList.size() > 0) {
            return movieList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<TheatreList> sportList) {
        movieList.addAll(sportList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.thumbnail)
        ImageView coverImageView;

        @BindView(R.id.title)
        TextView titleTextView;

        @BindView(R.id.newsTitle)
        TextView newsTextView;

        @BindView(R.id.newsInfo)
        TextView infoTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            titleTextView.setText("");
            newsTextView.setText("");
            infoTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final TheatreList mSport = movieList.get(position);

            if (mSport.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(mSport.getImageUrl())
                        .into(coverImageView);
            }

            if (mSport.getTitle() != null) {
                titleTextView.setText(mSport.getTitle());
            }

            if (mSport.getSubTitle() != null) {
                newsTextView.setText(mSport.getSubTitle());
            }

            if (mSport.getInfo() != null) {
                infoTextView.setText(mSport.getInfo());
            }

            itemView.setOnClickListener(v -> {
                if (mSport.getImageUrl() != null) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(mSport.getImageUrl()));
                        itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: Image url is not correct");
                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;
        @BindView(R.id.buttonRetry)
        TextView buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buttonRetry.setOnClickListener(v -> mCallback.onEmptyViewRetryClick());
        }

        @Override
        protected void clear() {

        }

    }
}
