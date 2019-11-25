package com.cs360.simplestocks.activities.ui.home;

import com.cs360.simplestocks.utilities.GetStockData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
        GetStockData getStockData = new GetStockData();
        getStockData.execute();
    }

    public LiveData<String> getText() {
        return mText;
    }
}