package urbantech.presentation.mainactivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import urbantech.presentation.mainactivity.ItemAdapter.OnItemListener;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final ImageView imageView;
    final TextView nameView;
    final TextView brandView;
    final TextView priceView;
    final Button compareButton;
    final OnItemListener onItemListener;

    public ItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        brandView = itemView.findViewById(R.id.brand);
        priceView = itemView.findViewById(R.id.price);
        compareButton = itemView.findViewById(R.id.compareButton);

        this.onItemListener = onItemListener;
        compareButton.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition());
    }
}
