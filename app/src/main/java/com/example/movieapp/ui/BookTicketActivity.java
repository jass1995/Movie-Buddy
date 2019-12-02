package com.example.movieapp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookTicketActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.backBtn)
    RelativeLayout backBtn;
    @BindView(R.id.tvMovieFirst)
    TextView tvMovieFirst;
    @BindView(R.id.tvMovieSecond)
    TextView tvMovieSecond;
    @BindView(R.id.tvMovieThird)
    TextView tvMovieThird;
    @BindView(R.id.tvMovieFour)
    TextView tvMovieFour;
    @BindView(R.id.btNext)
    Button btNext;

    AlertDialog.Builder builder;
    int qt = 20;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
            tvMovieFour.setText("IT Chapter Two");

        } else if (item.equals("10:30 PM")) {

            tvMovieFirst.setText("War");
            tvMovieSecond.setText("Joker");
            tvMovieThird.setText("Sky is Pink");
            tvMovieFour.setText("Nikka Zaildar 3");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ticket);
        ButterKnife.bind(this);

        backBtn.setOnClickListener(view -> finish());

        // Spinner element
        final Spinner spinner = findViewById(R.id.spinner);
        final Spinner spinner1 = findViewById(R.id.spinnerQty);

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
        ArrayAdapter<String> dataAdapterQty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, qty);

        // Drop down layout style - list view with radio button
        dataAdapterQty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapterQty);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                try {
                    qt = qt * Integer.parseInt(item);
                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        builder = new AlertDialog.Builder(this);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Setting message manually and performing action on button click
                builder.setMessage("Total Amount of book ticket is " + qt + "$")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("BOOK TICKET");
                alert.show();
            }
        });

        tvMovieFirst.setOnClickListener(view -> {
            tvMovieSecond.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieThird.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieFour.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieFirst.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        });
        tvMovieSecond.setOnClickListener(view -> {
            tvMovieSecond.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tvMovieThird.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieFour.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieFirst.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        });
        tvMovieThird.setOnClickListener(view -> {
            tvMovieSecond.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieThird.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tvMovieFour.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieFirst.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        });
        tvMovieFour.setOnClickListener(view -> {
            tvMovieSecond.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieThird.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tvMovieFour.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tvMovieFirst.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        });
    }
}
