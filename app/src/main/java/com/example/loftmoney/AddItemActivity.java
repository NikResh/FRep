package com.example.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.loftmoney.databinding.ActivityAddItemBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class AddItemActivity extends AppCompatActivity {

    public static final String Arg_Position = "arg_position";
    private ActivityAddItemBinding binding;
    CompositeDisposable = new CompositeDisposable();
    private int currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentPosition = extras.getInt(Arg_Position);
        }

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemToInternet();
            }
        });
    }

    private void putItemToInternet() {
        String type;
        if (currentPosition == 0) {
            type = "expense";
        } else {
            type = "income";
        }
        Disposable disposable = ((LoftAPP)getApplication()).loftAPI
                .putItems(binding.price.getText().toString(), binding.name.getText().toString(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    finish();
                }, throwable -> {
                    showMessage(R.string.error_put_items);
                });
        сompositeDisposable.add(disposable);
    }

    private void showMessage(int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}