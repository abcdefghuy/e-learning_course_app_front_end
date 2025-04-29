package com.example.e_learningcourse.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.base.BaseViewModel;
import com.example.e_learningcourse.model.response.CategoryResponse;
import com.example.e_learningcourse.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends BaseViewModel {
    private final CategoryRepository repository;
    private final MutableLiveData<List<CategoryResponse>> categories = new MutableLiveData<>();

    public CategoryViewModel() {
        repository = new CategoryRepository();
    }
    public LiveData<List<CategoryResponse>> getCategories() {
        return categories;
    }

    public void fetchCategories() {
        repository.getAllCategories().observeForever(response -> {
            if (response == null || response.getData() == null) {
                return;
            }
            categories.setValue(response.getData());
        });
    }


}
