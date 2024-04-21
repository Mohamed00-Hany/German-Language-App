package com.projects.germanlanguageapp.ui.translation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TranslationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TranslationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Translation_page fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}