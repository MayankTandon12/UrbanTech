package urbantech.presentation.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import urbantech.presentation.category.CategoryAdapter.OnCategoryListener;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final ImageView imageView;
    final TextView nameView;
    final CardView cardView;
    final OnCategoryListener onCategoryListener;

    public CategoryViewHolder(@NonNull View categoryView, OnCategoryListener onCategoryListener) {
        super(categoryView);
        this.cardView = categoryView.findViewById(R.id.categoryCardView);
        this.nameView = cardView.findViewById(R.id.categoryName);
        this.imageView = cardView.findViewById(R.id.categoryImage);

        this.onCategoryListener = onCategoryListener;
        categoryView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onCategoryListener.onCategoryClick(getAdapterPosition());
    }
}