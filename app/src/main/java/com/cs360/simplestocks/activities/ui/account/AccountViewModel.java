package com.cs360.simplestocks.activities.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> userName;

    public AccountViewModel() {
        userName = new MutableLiveData<>();
        userName.setValue("");
    }

    public LiveData<String> getText() {

        return userName;
    }
}