package com.example.e_learningcourse.ui.payment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_learningcourse.model.request.PaymentRequest;
import com.example.e_learningcourse.model.response.ApiResponse;
import com.example.e_learningcourse.repository.PaymentRepository;
import com.example.e_learningcourse.ui.base.BaseViewModel;

public class PaymentViewModel extends BaseViewModel {

    private final PaymentRepository repository = new PaymentRepository();
    public final MutableLiveData<Long> userId = new MutableLiveData<>();
    public final MutableLiveData<Long> courseId = new MutableLiveData<>();
    public final MutableLiveData<Double> amount = new MutableLiveData<>();
    public final MutableLiveData<String> provider = new MutableLiveData<>("VNPAY");

    private final MutableLiveData<ApiResponse<String>> paymentResponse = new MutableLiveData<>();

    public LiveData<ApiResponse<String>> getPaymentResponse() {
        return paymentResponse;
    }

    public void createPayment() {
        if (!isValidInput()) return;

        setLoading(true);

        PaymentRequest request = new PaymentRequest(
                userId.getValue(),
                courseId.getValue(),
                amount.getValue(),
                provider.getValue()
                );

        repository.createPayment(request).observeForever(response -> {
            setLoading(false);
            paymentResponse.setValue(response);
        });
    }

    private boolean isValidInput() {
        if (userId.getValue() == null || courseId.getValue() == null || amount.getValue() == null) {
            return false;
        }
        return true;
    }
}