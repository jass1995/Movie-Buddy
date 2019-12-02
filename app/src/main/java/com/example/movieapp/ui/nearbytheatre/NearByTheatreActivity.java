package com.example.movieapp.ui.nearbytheatre;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.utils.CommonUtils;
import com.example.movieapp.utils.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearByTheatreActivity extends AppCompatActivity implements MovieAdapter.Callback {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    MovieAdapter mSportAdapter;
    @BindView(R.id.backBtn)
    RelativeLayout backBtn;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_theatre);
        ButterKnife.bind(this);
        setUp();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUp() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mSportAdapter = new MovieAdapter(new ArrayList<>());

        prepareDemoContent();
    }

    private void prepareDemoContent() {
        CommonUtils.showLoading(NearByTheatreActivity.this);
        new Handler().postDelayed(() -> {
            //prepare data and show loading
            CommonUtils.hideLoading();
            ArrayList<TheatreList> mSports = new ArrayList<>();
            String[] sportsList = getResources().getStringArray(R.array.theatre_list);
            String[] sportsInfo = getResources().getStringArray(R.array.theatre_info);
            String[] sportsImage = getResources().getStringArray(R.array.theatre_images);
            for (int i = 0; i < sportsList.length; i++) {
                mSports.add(new TheatreList(sportsImage[i], sportsInfo[i], "Theatre", sportsList[i]));
            }
            mSportAdapter.addItems(mSports);
            mRecyclerView.setAdapter(mSportAdapter);
        }, 2000);


    }

    @Override
    public void onEmptyViewRetryClick() {
        prepareDemoContent();
    }
}
