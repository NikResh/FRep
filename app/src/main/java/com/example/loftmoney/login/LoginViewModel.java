package com.example.loftmoney.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loftmoney.api.AuthResponse;
import com.example.loftmoney.api.AuthorizationApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<String> authToken = new MutableLiveData<>();
    MutableLiveData<String> message = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    void makeLogin(AuthorizationApi authAPI, String userId) {
        compositeDisposable.add(authAPI.makeLogin(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthResponse>() {
                    @Override
                    public void accept(AuthResponse authResponse) throws Exception {
                        authToken.postValue(authResponse.getAuthToken());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        message.postValue(throwable.getMessage());
                    }
                }));
    }
}
