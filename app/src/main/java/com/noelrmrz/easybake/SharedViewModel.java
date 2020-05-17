package com.noelrmrz.easybake;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SharedViewModel extends AndroidViewModel {
    private MutableLiveData<String> message = new MutableLiveData<>();

    public SharedViewModel(Application application) {
        super(application);
    }

    public void setMessage(String message) {

        this.message.setValue(message);
    }

    public LiveData<String> getMessage() {
        return message;
    }
}
