package com.giftgenius.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class FavoritesFragment extends Fragment {
    private RecyclerView rvFavorites;
    private TextView tvEmpty;
    private GiftAdapter giftAdapter;
    private GiftViewModel viewModel;
    private List<Gift> favoriteList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setupViewModel();
        setupAdapter();
    }

    private void initViews(View view) {
        rvFavorites = view.findViewById(R.id.rv_favorites);
        tvEmpty = view.findViewById(R.id.tv_empty_favorites);
    }

    private void setupViewModel() {
        viewModel = new GiftViewModel(GiftRepository.getInstance());
        viewModel.getFavorites().observe(getViewLifecycleOwner(), favorites -> {
            if (favorites != null && !favorites.isEmpty()) {
                favoriteList.clear();
                favoriteList.addAll(favorites);
                giftAdapter.notifyDataSetChanged();
                rvFavorites.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.GONE);
            } else {
                rvFavorites.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
        viewModel.loadFavorites();
    }

    private void setupAdapter() {
        giftAdapter = new GiftAdapter(favoriteList, gift -> {
            viewModel.toggleFavorite(gift);
        });
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavorites.setAdapter(giftAdapter);
    }
}
