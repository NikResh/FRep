package com.example.loftmoney.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.loftmoney.BalanceView;
import com.example.loftmoney.R;

import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class DiagramFragment extends Fragment {

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        BalanceView balanceView = view.findViewById(R.id.BalanceView);
        Button randomView = view.findViewById(R.id.randomButton);
        randomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balanceView.update(new Random().nextFloat(), new Random().nextFloat());

            }
        });
    }

    public static DiagramFragment getInstance() {return new DiagramFragment();}
}
