package com.example.movieapp.ui.ticket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TicketViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TicketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tickets ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}