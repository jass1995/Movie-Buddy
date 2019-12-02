package com.example.movieapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MovieApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        Realm.init(getApplicationContext());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("TheatreList.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
