package com.example.loftmoney.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loftmoney.AddItemActivity;
import com.example.loftmoney.LoftAPP;
import com.example.loftmoney.databinding.FragmentBudgetBinding;
import com.example.loftmoney.items.ItemsAdapter;


public class BudgetFragment extends Fragment {

    private static final String Arg_Current_Position = "current_position";
    public static final String Arg_Add_Item = "arg_add_item";
    public static final int REQ_CODE = 125;

    private FragmentBudgetBinding binding;
    private BudgetViewModel mainViewModel;
    private int currentPosition;
    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureRecyclerView();
        configureViewModel();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
                public void onRefresh(){
                    mainViewModel.loadData(((LoftAPP) getActivity().getApplication()).loftAPI, currentPosition);
            }
        });

        binding.addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                intent.putExtra(AddItemActivity.Arg_Position, currentPosition);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mainViewModel.loadData(((LoftAPP) getActivity().getApplication()).loftAPI, currentPosition);
    }


    private void configureRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvItems.setLayoutManager(layoutManager);
        binding.rvItems.setAdapter(itemsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        binding.rvItems.addItemDecoration(dividerItemDecoration);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPosition = getArguments().getInt(Arg_Current_Position);
        }
    }

    private void configureViewModel() {
        mainViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        mainViewModel.itemsLiveData.observe(getViewLifecycleOwner(), moneyItems -> {
            itemsAdapter.setData(moneyItems);
        });

        mainViewModel.swipeRefresh.observe(getViewLifecycleOwner(), isRefresh -> {
            binding.swipeRefresh.setRefreshing(isRefresh);
        });

        mainViewModel.messageString.observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        });

        mainViewModel.messageInt.observe(getViewLifecycleOwner(), messageInt -> {
            Toast.makeText(getActivity(), getString(messageInt), Toast.LENGTH_LONG).show();
        });
    }


    public static BudgetFragment newInstance (int position) {
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(Arg_Current_Position, position);
        budgetFragment.setArguments(args);

        return budgetFragment;
    }
}