package com.example.invenstory.ui.shareItem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareItemViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShareItemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This has not been implemented yet");
    }

    public LiveData<String> getText() {
        return mText;
    }
}