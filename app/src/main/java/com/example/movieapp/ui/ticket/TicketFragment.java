package com.example.movieapp.ui.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.movieapp.R;
import com.example.movieapp.ui.nearbytheatre.NearByTheatreActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

public class TicketFragment extends Fragment /*implements MovieAdapter.Callback*/ implements AdapterView.OnItemSelectedListener {

    private TicketViewModel toolsViewModel;
    TextView tvMovieFirst,tvMovieSecond,tvMovieThird,tvMovieFour;
    Button btNext;

  /*  @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    MovieAdapter mTheatreAdapter;

    LinearLayoutManager mLayoutManager;*/

   /* private void setUp() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mTheatreAdapter = new MovieAdapter(new ArrayList<>());

        prepareDemoContent();
    }*/
   /* private void prepareDemoContent() {
        CommonUtils.showLoading(getActivity());
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
            mTheatreAdapter.addItems(mSports);
            mRecyclerView.setAdapter(mTheatreAdapter);
        }, 2000);


    }

    @Override
    public void onEmptyViewRetryClick() {
        prepareDemoContent();
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(TicketViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ticket, container, false);
        // mRecyclerView=root.findViewById(R.id.mRecyclerView);
        ButterKnife.bind(Objects.requireNonNull(getActivity()));
        // setUp();
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        tvMovieFirst=root.findViewById(R.id.tvMovieFirst);
        tvMovieSecond=root.findViewById(R.id.tvMovieSecond);
        tvMovieThird=root.findViewById(R.id.tvMovieThird);
        tvMovieFour=root.findViewById(R.id.tvMovieFour);
        btNext=root.findViewById(R.id.btNext);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), NearByTheatreActivity.class);
                startActivity(intent);
            }
        });

        // Spinner element
        final Spinner spinner = root.findViewById(R.id.spinner);
        final Spinner spinner1 = root.findViewById(R.id.spinnerQty);

        // Spinner Drop down elements
        List<String> qty = new ArrayList<String>();
        qty.add("1");
        qty.add("2");
        qty.add("3");
        qty.add("4");
        qty.add("5");
        qty.add("6");
        qty.add("7");
        qty.add("8");
        qty.add("9");
        qty.add("10");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterQty = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, qty);

        // Drop down layout style - list view with radio button
        dataAdapterQty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapterQty);


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("10:30 AM");
        categories.add("12:00 PM");
        categories.add("01:30 PM");
        categories.add("03:00 PM");
        categories.add("05:15 PM");
        categories.add("07:30 PM");
        categories.add("09:35 PM");
        categories.add("10:30 PM");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();

        if (item.equals("10:30 AM")) {
            Log.e("item:", "" + item);
            tvMovieFirst.setText("Joker");
            tvMovieSecond.setText("Downton Abbey");
            tvMovieThird.setText("IT Chapter Two");
            tvMovieFour.setText("Durj");
        } else if (item.equals("12:00 PM")) {
            Log.e("item:", "" + item);
            tvMovieFirst.setText("Joker");
            tvMovieSecond.setText("Gemini Man");
            tvMovieThird.setText("Abominable");
            tvMovieFour.setText("Hustlers");

        } else if (item.equals("01:30 PM")) {
            Log.e("item:", "" + item);
            tvMovieFirst.setText("Cumini Man");
            tvMovieSecond.setText("Tara Mira");
            tvMovieThird.setText("Sky is Pink");
            tvMovieFour.setText("IT Chapter Two");

        } else if (item.equals("03:00 PM")) {
            Log.e("item:", "" + item);
            tvMovieFirst.setText("Joker");
            tvMovieSecond.setText("Durj");
            tvMovieThird.setText("Sky is Pink");
            tvMovieFour.setText("Downton Abbey");

        } else if (item.equals("05:15 PM")) {
            Log.e("item:", "" + item);
            tvMovieFirst.setText("War");
            tvMovieSecond.setText("Abominable");
            tvMovieThird.setText("Hustlers");
            tvMovieFour.setText("Gemini Man");

        } else if (item.equals("07:30 PM")) {
            Log.e("item:", "" + item);
            tvMovieFirst.setText("Nikka Zaildar 3");
            tvMovieSecond.setText("Addamas Family");
            tvMovieThird.setText("IT Chapter Two");
            tvMovieFour.setText("Tara Mira");

        } else if (item.equals("09:35 PM")) {
            Log.e("item:", "" + item);
            tvMovieFirst.setText("Sky is Pink");
            tvMovieSecond.setText("Nikka Zaildar 3");
            tvMovieThird.setText("Durj");
            tvMovieFour.setText("");

        } else if (item.equals("10:30 PM")) {

            tvMovieFirst.setText("War");
            tvMovieSecond.setText("Joker");
            tvMovieThird.setText("Sky is Pink");
            tvMovieFour.setText("");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}