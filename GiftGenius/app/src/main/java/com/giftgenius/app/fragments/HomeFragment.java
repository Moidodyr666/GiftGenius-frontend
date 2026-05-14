package com.giftgenius.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.giftgenius.app.R;
import com.giftgenius.app.adapters.GiftAdapter;
import com.giftgenius.app.models.Gift;
import com.giftgenius.app.repository.GiftRepository;
import com.giftgenius.app.viewmodels.GiftViewModel;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private EditText etRecipient, etOccasion, etInterests, etBudget;
    private Button btnGenerate;
    private RecyclerView rvResults;
    private ProgressBar progressBar;
    private TextView tvEmpty, tvError, tvOffline;
    private GiftAdapter giftAdapter;
    private GiftViewModel viewModel;
    private List<Gift> giftList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setupViewModel();
        setupAdapter();
        setupListeners();
    }

    private void initViews(View view) {
        etRecipient = view.findViewById(R.id.et_recipient);
        etOccasion = view.findViewById(R.id.et_occasion);
        etInterests = view.findViewById(R.id.et_interests);
        etBudget = view.findViewById(R.id.et_budget);
        btnGenerate = view.findViewById(R.id.btn_generate);
        rvResults = view.findViewById(R.id.rv_results);
        progressBar = view.findViewById(R.id.progress_bar);
        tvEmpty = view.findViewById(R.id.tv_empty);
        tvError = view.findViewById(R.id.tv_error);
        tvOffline = view.findViewById(R.id.tv_offline);
    }

    private void setupViewModel() {
        viewModel = new GiftViewModel(GiftRepository.getInstance());
        viewModel.getGifts().observe(getViewLifecycleOwner(), gifts -> {
            if (gifts != null && !gifts.isEmpty()) {
                giftList.clear();
                giftList.addAll(gifts);
                giftAdapter.notifyDataSetChanged();
                rvResults.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
            } else {
                rvResults.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                tvError.setText(error);
                tvError.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getOfflineMode().observe(getViewLifecycleOwner(), isOffline -> {
            tvOffline.setVisibility(isOffline ? View.VISIBLE : View.GONE);
        });
    }

    private void setupAdapter() {
        giftAdapter = new GiftAdapter(giftList, gift -> {
            viewModel.toggleFavorite(gift);
            Toast.makeText(getContext(),
                    gift.isFavorite() ? getString(R.string.added_to_favorites) :
                            getString(R.string.removed_from_favorites),
                    Toast.LENGTH_SHORT).show();
        });
        rvResults.setLayoutManager(new LinearLayoutManager(getContext()));
        rvResults.setAdapter(giftAdapter);
    }

    private void setupListeners() {
        btnGenerate.setOnClickListener(v -> {
            String recipient = etRecipient.getText().toString().trim();
            String occasion = etOccasion.getText().toString().trim();
            String interests = etInterests.getText().toString().trim();
            String budget = etBudget.getText().toString().trim();

            if (recipient.isEmpty() || occasion.isEmpty()) {
                Toast.makeText(getContext(), "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.generateGifts(recipient, occasion, interests, budget);
        });
    }
}
