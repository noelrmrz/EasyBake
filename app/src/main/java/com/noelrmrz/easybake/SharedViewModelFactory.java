package com.noelrmrz.easybake;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SharedViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;

    public SharedViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new SharedViewModel(mApplication);
    }
}
