package com.giftgenius.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.giftgenius.app.R;
import com.giftgenius.app.models.Gift;
import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.GiftViewHolder> {
    private List<Gift> giftList;
    private OnFavoriteClickListener favoriteListener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Gift gift);
    }

    public GiftAdapter(List<Gift> giftList, OnFavoriteClickListener listener) {
        this.giftList = giftList;
        this.favoriteListener = listener;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gift, parent, false);
        return new GiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        Gift gift = giftList.get(position);
        holder.bind(gift);
    }

    @Override
    public int getItemCount() {
        return giftList.size();
    }

    public void updateList(List<Gift> newGifts) {
        this.giftList = newGifts;
        notifyDataSetChanged();
    }

    class GiftViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivGiftImage, btnFavorite;
        private TextView tvName, tvPrice, tvDescription;
        private Button btnBuy;

        public GiftViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGiftImage = itemView.findViewById(R.id.iv_gift_image);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
            tvName = itemView.findViewById(R.id.tv_gift_name);
            tvPrice = itemView.findViewById(R.id.tv_gift_price);
            tvDescription = itemView.findViewById(R.id.tv_gift_description);
            btnBuy = itemView.findViewById(R.id.btn_buy);
        }

        public void bind(Gift gift) {
            tvName.setText(gift.getName());
            tvPrice.setText(String.format("%d ₽", gift.getPrice()));
            tvDescription.setText(gift.getDescription());

            if (gift.getImageUrl() != null && !gift.getImageUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(gift.getImageUrl())
                        .placeholder(R.drawable.ic_gift_placeholder)
                        .into(ivGiftImage);
            }

            btnFavorite.setImageResource(
                    gift.isFavorite() ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite
            );

            btnFavorite.setOnClickListener(v -> {
                if (favoriteListener != null) {
                    favoriteListener.onFavoriteClick(gift);
                }
            });

            btnBuy.setOnClickListener(v -> {
                // Открытие ссылки на маркетплейс
            });
        }
    }
}
