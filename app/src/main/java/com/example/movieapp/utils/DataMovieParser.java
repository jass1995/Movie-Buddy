package com.example.movieapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataMovieParser {
    public List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            Log.d("Places", "parse");
            jsonObject = new JSONObject((String) jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            Log.d("Places", "parse error");
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;
        Log.d("Places", "getPlaces");

        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
                Log.d("Places", "Adding places");

            } catch (JSONException e) {
                Log.d("Places", "Error in Adding places");
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        String icon = "";
        int rating = 0;
        int user_ratings_total = 0;


        Log.d("getPlace", "Entered");

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }

            if (!googlePlaceJson.isNull("rating")) {
                rating = googlePlaceJson.getInt("rating");
            }

            if (!googlePlaceJson.isNull("icon")) {
                icon = googlePlaceJson.getString("icon");
            }

            if (!googlePlaceJson.isNull("user_ratings_total")) {
                user_ratings_total = googlePlaceJson.getInt("user_ratings_total");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("icon", icon);
            googlePlaceMap.put("reference", reference);
            googlePlaceMap.put("rating", ""+rating);
            googlePlaceMap.put("user_ratings_total", ""+user_ratings_total);
            Log.d("getPlace", "Putting Places");
        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
}
