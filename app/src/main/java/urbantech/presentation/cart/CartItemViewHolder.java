package urbantech.presentation.cart;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import urbantech.presentation.cart.CartItemAdapter.OnItemListener;

public class CartItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final ImageView imageView;
    final TextView nameView;
    final TextView brandView;
    final TextView priceView;
    final TextView qtyView;
    final OnItemListener onItemListener;

    final Button increaseButton;
    final Button decreaseButton;
    final Button deleteItemButton;

    public CartItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        brandView = itemView.findViewById(R.id.brand);
        priceView = itemView.findViewById(R.id.price);
        qtyView = itemView.findViewById(R.id.qty);
        increaseButton = itemView.findViewById(R.id.qtyUp);
        decreaseButton = itemView.findViewById(R.id.qtyDown);
        deleteItemButton = itemView.findViewById(R.id.garbage);

        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        increaseButton.setOnClickListener(this);
        decreaseButton.setOnClickListener(this);
        deleteItemButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition());
    }
}
