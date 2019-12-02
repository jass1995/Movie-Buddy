package com.example.movieapp.database;


import androidx.annotation.NonNull;

import com.example.movieapp.ui.nearbytheatre.TheatreList;
import com.example.movieapp.ui.profile.ProfileDataSave;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RecordsDao {

    private Realm mRealm;
    private RealmQuery<ProfileDataSave> profileDataSave;

    public RealmQuery<ProfileDataSave> loadProfileData() {
        profileDataSave=mRealm.where(ProfileDataSave.class);
        return profileDataSave;
    }

    public void updateProfile(final String profileId,String firstName,String lastName) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ProfileDataSave profileDataSave = realm.where(ProfileDataSave.class).equalTo("id", profileId).findFirst();
                profileDataSave.setmFirstName(firstName);
                profileDataSave.setmLastName(lastName);
            }
        });
    }

    public void saveProfile(ProfileDataSave profileDataSave) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(profileDataSave);
            }
        });
    }

    public void updateSaveProfile(ProfileDataSave profileDataSave) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(profileDataSave);
            }
        });
    }

    public RecordsDao(@NonNull Realm realm) {
        mRealm = realm;
    }

}
