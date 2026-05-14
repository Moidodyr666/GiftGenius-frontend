package com.giftgenius.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.giftgenius.app.R;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvTitle = view.findViewById(R.id.tv_profile_title);
        Button btnClear = view.findViewById(R.id.btn_clear_data);

        tvTitle.setText("Профиль");

        btnClear.setOnClickListener(v -> {
            // Очистка локальных данных
            requireActivity().getSharedPreferences("giftgenius_prefs", 0)
                    .edit().clear().apply();
        });
    }
}
