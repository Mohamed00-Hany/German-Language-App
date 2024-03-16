package com.projects.germanlanguageapp.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Translation_page_ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Translation_page_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Translation_page fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}