package com.example.movieapp.ui.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.movieapp.R;



public class ReviewFragment extends Fragment {

    private ReviewViewModel reviewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_review, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        /*homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}