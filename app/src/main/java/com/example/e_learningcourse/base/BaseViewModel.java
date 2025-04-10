package com.example.e_learningcourse.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    protected MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    protected MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    protected void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    protected void setError(String message) {
        errorMessage.setValue(message);
    }
}
